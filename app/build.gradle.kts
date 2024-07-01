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
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
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
