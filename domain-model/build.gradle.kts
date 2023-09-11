plugins {
    id("java-library")
    id("java-test-fixtures")
}

group = "de.hbt.cfa"
version = "0.0.3-SNAPSHOT"

repositories {
    mavenCentral()
}

val mapstructVersion = "1.5.5.Final"

dependencies {
    annotationProcessor(platform(project(":platform")))

    implementation(platform(project(":platform")))

    annotationProcessor("org.mapstruct:mapstruct-processor")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding")

    compileOnly("org.projectlombok:lombok")

    implementation("org.mapstruct:mapstruct")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("com.fasterxml.jackson.core:jackson-databind")
}

tasks.test {
    useJUnitPlatform()
}
