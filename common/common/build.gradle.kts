@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
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
        val commonMain by getting {
            dependencies {
                implementation(libs.wire.client)
                implementation(libs.wire.server)
                implementation(libs.kotlinx.serialization.json)
            }
        }
    }
}

android {
    namespace = "org.ironlions.common"
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
    kotlin { }

    sourcePath {
        srcDir("src/commonMain/proto")
    }
}
