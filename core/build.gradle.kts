plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation(libs.strikt.core)
}

tasks.test {
    useJUnitPlatform()
}
