import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.android.kotlin)
    alias(libs.plugins.kapt)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.parcelize)
    alias(libs.plugins.ksp)
}

apply {
    from("$rootDir/base-module.gradle")
}


val credentialsPropertiesFile = rootProject.file("credentials.properties")
val credentialsProperties = Properties()
if (credentialsPropertiesFile.exists()) {
    credentialsProperties.load(credentialsPropertiesFile.inputStream())
} else {
    throw GradleException("Missing credentials.properties file.")
}

android {
    compileSdk = AndroidConfig.compileSDK

    defaultConfig {
        applicationId = AndroidConfig.applicationId
        minSdk = AndroidConfig.minSDK
        targetSdk = AndroidConfig.targetSDK
        versionCode = AndroidConfig.versionCode
        versionName = AndroidConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/v1/\"")
            buildConfigField("String", "CAT_API_KEY", "\"${credentialsProperties["CAT_API_KEY"]}\"")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            buildConfigField("String", "BASE_URL", "\"https://api.thecatapi.com/v1/\"")
            buildConfigField("String", "CAT_API_KEY", "\"${credentialsProperties["CAT_API_KEY"]}\"")
        }
    }

    compileOptions {
        sourceCompatibility = AndroidConfig.javaVersion
        targetCompatibility = AndroidConfig.javaVersion
    }
    kotlinOptions {
        jvmTarget = AndroidConfig.jvmTarget
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

    packaging {
        resources {
            excludes += setOf(
                "META-INF/LICENSE-notice.md",
                "META-INF/LICENSE.md",
                "META-INF/DEPENDENCIES",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/LICENSE.txt"
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    namespace = "com.aliumujib.catbrowser"

    applicationVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir("build/generated/ksp/$name/kotlin")
            }
        }
    }
}

dependencies {
    // Modules
    implementation(projects.core.common)
    implementation(projects.core.models)
    implementation(projects.core.preferences)
    implementation(projects.core.designsystem)
    implementation(projects.core.network)
    implementation(projects.core.commonDomain)
    implementation(projects.feature.settings)
    implementation(projects.feature.breeds.allBreeds)
    implementation(projects.feature.breeds.favoriteBreeds)
    implementation(projects.feature.breeds.common)
    implementation(projects.feature.breeds.breedDetails)
    implementation(projects.feature.breeds.breedsDomain)
    implementation(projects.feature.breeds.breedsData)

    // RamCosta Navigation
    implementation(libs.compose.destinations.animations)
    implementation(libs.androidx.media3.session)
    ksp(libs.compose.destinations.ksp)

    implementation(libs.android.material)

    // Splash Screen API
    implementation(libs.core.splash.screen)
}
