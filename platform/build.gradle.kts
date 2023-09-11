plugins {
    // Platform creation
    `java-platform`
}

group = "de.hbt.cfa"
version = "0.0.3-SNAPSHOT"

repositories {
    mavenCentral()
}

javaPlatform {
    allowDependencies()
}

val mapstructVersion = "1.5.5.Final"

dependencies {
    api(platform("org.springframework.boot:spring-boot-dependencies:3.1.2"))

    api("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    api("org.mapstruct:mapstruct-processor:${mapstructVersion}")

    api("org.mapstruct:mapstruct:${mapstructVersion}")
}
