import org.openapitools.generator.gradle.plugin.tasks.GenerateTask
import org.springframework.boot.gradle.tasks.bundling.BootBuildImage

plugins {
    java
    id("org.springframework.boot") version "3.1.2"
    id("org.openapi.generator") version "7.0.0" // used to demonstrate the build cache
    id("com.dorongold.task-tree") version "2.1.1" // to look at the task tree
}

group = "de.hbt.cfa"
version = "0.0.3-SNAPSHOT"

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

// Enable dependency locking
//dependencyLocking {
//    lockAllConfigurations()
//}

val mapstructVersion = "1.5.5.Final"

dependencies {
    annotationProcessor(platform(project(":platform")))
    annotationProcessor("org.projectlombok:lombok")

    implementation(platform(project(":platform")))

    implementation(project(":domain-model"))

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("com.h2database:h2")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")

    testImplementation(testFixtures(project(":domain-model")))
}

/**
 * Demonstrate how to create a new source set for integration tests
 * and add a new task to run the integration tests after the unit tests.
 */

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation: Configuration by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

val integrationTestRuntimeOnly: Configuration by configurations.getting {
    extendsFrom(configurations.runtimeOnly.get())
}

dependencies {
    integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationTestRuntimeOnly("org.springframework.boot:spring-boot-starter-webflux")
}

// register the new task for running the integration tests
val integrationTest = tasks.register<Test>("integrationTest") {
    description = "Runs the integration tests"
    group = "verification"
    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    // run integration tests after unit tests
    mustRunAfter(tasks["test"])
}

// Include this task as part of the check task to make sure the integration tests run
//tasks.check {
//    dependsOn(integrationTest)
//}

/////////////////////////////

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

// Run the api generation task before the compile task
//tasks.compileJava { dependsOn("generateApi") }

/////////////////////////////

// Include this task to create a valid container image with ./gradlew bootBuildImage
//tasks.named<BootBuildImage>("bootBuildImage") {
//    imageName.set("${rootProject.name.lowercase()}:${project.version}")
//    environment.set(environment.get() + mapOf("BP_JVM_VERSION" to "17", "BP_SPRING_CLOUD_BINDINGS_DISABLED" to "true"))
//    buildpacks.set(listOf("paketo-buildpacks/ca-certificates", "gcr.io/paketo-buildpacks/amazon-corretto", "paketo-buildpacks/java"))
//}

/////////////////////////////
