@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.wire)
}

val extJvmTarget: String by project
val extSourceCompatibility: JavaVersion by project
val extTargetCompatibility: JavaVersion by project

kotlin {
    androidTarget()

    sourceSets {
        val androidMain by getting {
            dependencies {
                /* Android */
                implementation(libs.androidx.core)
                implementation(libs.androidx.appcompat)
                implementation(libs.material)

                /* Common */
                implementation(project(":common:common"))
            }
        }

        val androidUnitTest by getting {
            dependencies {
                implementation(libs.junit)
                implementation(libs.mockito)
            }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.androidx.test.junit)
                implementation(libs.androidx.test.expresso)
            }
        }
    }
}

android {
    namespace = "org.ironlions.panopticon"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = extSourceCompatibility
        targetCompatibility = extTargetCompatibility
    }
}

wire {
    kotlin {
        rpcRole = "client"
        rpcCallStyle = "suspending"
    }

    sourcePath {
        srcDir(project(":common:common").path + "/src/commonMain/proto")
    }
}