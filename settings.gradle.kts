rootProject.name = "Carcassonne"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":core")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
