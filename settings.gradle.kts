rootProject.name = "Carcassonne"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(
    ":core",
    ":ui:android",
    ":ui:desktop",
    ":ui:common"
)
