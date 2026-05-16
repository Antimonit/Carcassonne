import org.gradle.kotlin.dsl.desktop
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.compose.hotReload)
}

compose.desktop {
    application {
        mainClass = "me.khol.carcassonne.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Carcassonne"
            packageVersion = "1.0.1"
            vendor = "David Khol"
        }
    }
}

dependencies {
    implementation(projects.composeApp)
    implementation(compose.desktop.currentOs)
}
