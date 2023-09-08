plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.0")
    implementation("com.opencsv:opencsv:4.6")
}

//configurations.all {
//    resolutionStrategy {
//        failOnVersionConflict()
//    }
//}