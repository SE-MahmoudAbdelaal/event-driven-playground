plugins {
    // If no Java 21 JDK is installed, Gradle downloads one automatically
    // (see the "toolchain" block in build.gradle.kts).
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "event-driven-playground"
