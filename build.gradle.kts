import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `java-library`
    kotlin("jvm") version "1.3.0"
    id("com.diffplug.gradle.spotless") version "3.16.0"
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation(kotlin("stdlib-jdk8"))
    testImplementation("org.mockito:mockito-junit-jupiter:2.23.4")
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
    withType<Test> {
        useJUnitPlatform()
    }
}

// tag::tag_filter[]
tasks.register<Test>("integrationTest") {
    useJUnitPlatform {
        includeTags("integration")
    }
}
// end::tag_filter[]

spotless {
    val headerFile = file("gradle/apache-license-2.0.java")
    java {
        licenseHeaderFile(headerFile, "(package|import|open|module) ")
        removeUnusedImports()
        trimTrailingWhitespace()
        endWithNewline()
    }
    kotlin {
        licenseHeaderFile(headerFile)
        trimTrailingWhitespace()
        endWithNewline()
    }
}
