enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io")
        maven("https://androidx.dev/storage/compose-compiler/repository/") {
            content {
                includeGroup("androidx.compose.compiler")
            }
        }
    }
}
rootProject.name = "CatBreedBrowser"
include(":app")
include(":feature:settings")
include(":feature:breeds:all-breeds")
include(":feature:breeds:favorite-breeds")
include(":feature:breeds:common")
include(":feature:breeds:breed-details")
include(":feature:breeds:breeds-data")
include(":feature:breeds:breeds-domain")
include(":core:designsystem")
include(":core:models")
include(":core:common")
include(":core:common-test")
include(":core:database")
include(":core:analytics")
include(":core:preferences")
include(":core:network")
include(":core:common-domain")
