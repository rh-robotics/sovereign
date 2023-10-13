plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.dokka") version "1.9.0"
}

android {
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
            includes.from(project.files(), "overview.md")
        }
    }
}

dependencies {
    /* FTC */
    implementation("org.firstinspires.ftc:Inspection:9.0.0")
    implementation("org.firstinspires.ftc:Blocks:9.0.0")
    implementation("org.firstinspires.ftc:Tfod:9.0.0")
    implementation("org.firstinspires.ftc:RobotCore:9.0.0")
    implementation("org.firstinspires.ftc:RobotServer:9.0.0")
    implementation("org.firstinspires.ftc:OnBotJava:9.0.0")
    implementation("org.firstinspires.ftc:Hardware:9.0.0")
    implementation("org.firstinspires.ftc:FtcCommon:9.0.0")
    implementation("org.firstinspires.ftc:Vision:9.0.0")
    implementation("org.firstinspires.ftc:gameAssets-CenterStage:1.0.0")
    implementation("com.acmerobotics.roadrunner:core:1.0.0-beta3")
    implementation("com.acmerobotics.roadrunner:actions:1.0.0-beta3")

    /* Android */
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}