import org.gradle.api.JavaVersion

/**
 * A collection of configuration properties for Android modules.
 */
object AndroidConfig {
    const val minSDK = 26
    const val targetSDK = 34
    const val compileSDK = 34
    const val versionCode = 21
    const val versionName = "0.0.1"
    const val applicationId = "com.aliumujib.takehomestarter"

    val javaVersion = JavaVersion.VERSION_17
    const val jvmTarget = "17"
}