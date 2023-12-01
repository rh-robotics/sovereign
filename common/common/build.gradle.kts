@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.wire)
}

val extJvmTarget: String by project
val extSourceCompatibility: JavaVersion by project
val extTargetCompatibility: JavaVersion by project

kotlin {
    jvm()

    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            kotlinOptions {
                jvmTarget = extJvmTarget
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.wire)
                api(libs.wire)
            }
        }
    }
}

android {
    namespace = "org.ironlions.common.panopticon.proto"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    compileOptions {
        sourceCompatibility = extSourceCompatibility
        targetCompatibility = extTargetCompatibility
    }
}

java {
    sourceCompatibility = extSourceCompatibility
    targetCompatibility = extTargetCompatibility
}

wire {
    kotlin {}

    sourcePath {
        srcDir("src/commonMain/proto")
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = extJvmTarget
}
