@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.dokka)
    alias(libs.plugins.ksp)
}

kotlin {
    jvm()

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(libs.ksp.symbolProcessingApi)
                implementation(libs.javapoet)
            }
        }
    }
}