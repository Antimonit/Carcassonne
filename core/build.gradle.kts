import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.powerAssert)
    alias(libs.plugins.kover)
}

kotlin {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser {
            testTask {
                useKarma {
                    useChromeHeadless()
                }
            }
        }
    }
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.coroutines.test)
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
