plugins {
    kotlin("jvm")
    id("com.google.protobuf") version "0.9.4"
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.25.0"
    }
}

tasks {
    withType<Copy> {
        filesMatching("**/*.proto") {
            duplicatesStrategy = DuplicatesStrategy.INCLUDE
        }
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:3.25.0")
}