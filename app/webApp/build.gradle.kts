import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName = "carcassonne"
        browser()
        binaries.executable()
    }

    sourceSets {
        // TODO: https://youtu.be/Atvl0l7fm1Y?si=TcvlN_0bxWI3t31s&t=705
        webMain.dependencies {
            implementation(projects.core)
            implementation(projects.ui)

            implementation(libs.compose.ui)
            implementation(libs.compose.material3)
            implementation(libs.compose.components.resources)
        }
    }
}