import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

plugins {
    java
    id("org.springframework.boot") version "3.1.1"
    id("io.spring.dependency-management") version "1.1.0"
    id("org.openapi.generator") version "7.0.0" // used to demonstrate the build cache
}

group = "de.hbt.cfa"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}


val mapstructVersion = "1.5.5.Final"

dependencies {
    annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")

    implementation("org.mapstruct:mapstruct:${mapstructVersion}")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.springframework.boot:spring-boot-starter-webflux")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

/**
 * Demonstration of the build cache when using the openapi-generator plugin
 * https://docs.gradle.org/current/userguide/build_cache.html
 *
 * api.yaml is defined as an input file
 * @see{GenerateTask.kt:102}
 *
 * Gradle will watch the file and call this task only when the file changes.
 */
tasks.register<GenerateTask>("generateApi") {
    generatorName.set("spring")

    inputSpec.set("$rootDir/src/main/resources/api/api.yaml")
    outputDir.set("$rootDir/build/generated/sources/api")

    configOptions.set(mapOf("dateLibrary" to "java8", "useSpringBoot3" to "true"))
    modelPackage.set("de.hbt.cfa.model")
    apiPackage.set("de.hbt.cfa.api")
}

// Uncomment to run the api generation task before the compile task
// don't forget to reload the gradle changes
//tasks.compileJava { dependsOn("generateApi") }