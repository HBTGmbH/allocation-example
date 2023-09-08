plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.apache.commons:commons-lang3:3.0") {
//        version {
//            strictly("3.0")
//            because("later versions have a bug impacting this application")
//        }
    }
    implementation("com.opencsv:opencsv:4.6")
}

//configurations.all {
//    resolutionStrategy {
//        failOnVersionConflict()
//    }
//}