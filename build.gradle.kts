@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.wire).apply(false)
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.brott.dev")
        maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
        maven("https://maven.realrobotix.me/imgui-java")
    }
}

project.ext["extJvmTarget"] = "11"
project.ext["extSourceCompatibility"] = JavaVersion.VERSION_11
project.ext["extTargetCompatibility"] = JavaVersion.VERSION_11

