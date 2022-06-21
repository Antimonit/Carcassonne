plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.application)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

group = "me.khol.carcassonne"
version = "1.0-SNAPSHOT"

android {
    namespace = "me.khol.carcassonne.android"

    compileSdk = 31
    defaultConfig {
        applicationId = "me.khol.carcassonne.android"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

dependencies {
    implementation(project(":ui:common"))
    implementation("androidx.activity:activity-compose:1.4.0")
}
