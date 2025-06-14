plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.powerAssert)
    alias(libs.plugins.kover)
}

kotlin {
    jvm()
    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}

powerAssert {
    functions.addAll(
        "kotlin.test.assertEqAAuals",
        "kotlin.test.assertTrue",
        "kotlin.test.assertFalse",
        "kotlin.assert",
        "kotlin.require",
    )
}
