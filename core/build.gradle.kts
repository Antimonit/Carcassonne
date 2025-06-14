plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kover)
}

kotlin {
    jvm()
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.strikt.core)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
