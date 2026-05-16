rootProject.name = "Carcassonne"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":core")
include(":ui")
include(":app:androidApp")
include(":app:desktopApp")
include(":app:webApp")
