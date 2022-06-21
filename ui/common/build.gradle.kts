import org.jetbrains.compose.compose

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.android.library")
}

group = "me.khol.carcassonne"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    jvm("desktop") {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        named("commonMain") {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
                api(compose.material)
//                api(compose.materialIconsExtended)
                api(compose("org.jetbrains.compose.material:material-icons-extended-desktop"))
                api(compose.animationGraphics)
                api(compose.uiTooling)
                api(compose.preview)
            }
        }
        named("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        named("androidMain") {
            dependencies {
                api("androidx.appcompat:appcompat:1.4.2")
                api("androidx.core:core-ktx:1.8.0")
            }
        }
        named("androidTest") {
            dependencies {
                implementation("junit:junit:4.13.2")
            }
        }
        named("desktopMain") {
            dependencies {
                api(compose.preview)
            }
        }
        named("desktopTest")
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}