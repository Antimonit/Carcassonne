plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation("io.strikt:strikt-core:0.35.1")
}

tasks.test {
    useJUnitPlatform()
}
