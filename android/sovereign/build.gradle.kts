@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
}

val extJvmTarget: String by project
val extSourceCompatibility: JavaVersion by project
val extTargetCompatibility: JavaVersion by project

kotlin {
    androidTarget()

    sourceSets {
        val androidMain by getting {
            dependencies {
                /* FTC */
                implementation(libs.ftc.inspection)
                implementation(libs.ftc.blocks)
                implementation(libs.ftc.tfod)
                implementation(libs.ftc.core)
                implementation(libs.ftc.server)
                implementation(libs.ftc.onBotJava)
                implementation(libs.ftc.hardware)
                implementation(libs.ftc.common)
                implementation(libs.ftc.vision)
                implementation(libs.ftc.gameAssets.centerStage)
                implementation(libs.roadrunner.core)
                implementation(libs.roadrunner.actions)

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
    namespace = "org.ironlions.sovereign"
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

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = extJvmTarget
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            moduleName.set("Sovereign")
            suppressObviousFunctions.set(false) // For the FTC rookies
            suppressInheritedMembers.set(false) // For the FTC rookies
            includes.from(project.files(), "overview.md")
        }
    }
}

/* android {
    namespace = "org.ironlions.sovereign"
    compileSdk = 34

    defaultConfig {
        minSdk = 29
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            moduleName.set("Sovereign")
            suppressObviousFunctions.set(false) // For the FTC rookies
            suppressInheritedMembers.set(false) // For the FTC rookies
            includes.from(project.files(), "overview.md")
        }
    }
}

dependencies {
    /* FTC */
    implementation(libs.ftc.inspection)
    implementation(libs.ftc.blocks)
    implementation(libs.ftc.tfod)
    implementation(libs.ftc.core)
    implementation(libs.ftc.server)
    implementation(libs.ftc.onBotJava)
    implementation(libs.ftc.hardware)
    implementation(libs.ftc.common)
    implementation(libs.ftc.vision)
    implementation(libs.ftc.gameAssets.centerStage)
    implementation(libs.roadrunner.core)
    implementation(libs.roadrunner.actions)

    /* Android */
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    testImplementation(libs.mockito)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.androidx.test.expresso)

    /* Common */
    implementation(project(":common:common"))
} */