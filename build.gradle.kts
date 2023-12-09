@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.wire).apply(false)
    alias(libs.plugins.dokka)
}

allprojects {
    val extJvmTarget: String by project
    val project = this

    repositories {
        google()
        mavenCentral()
        maven("https://maven.brott.dev")
        maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
        maven("https://maven.realrobotix.me/imgui-java")
    }

    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
            compilerOptions.freeCompilerArgs.addAll(
                "-opt-in=kotlin.RequiresOptIn", "-Xallow-kotlin-package", "-progressive"
            )
        }

        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions.jvmTarget = extJvmTarget
        }

        withType<org.jetbrains.dokka.gradle.DokkaTaskPartial>().configureEach {
            dokkaSourceSets {
                configureEach {
                    moduleName.set(project.path.drop(1).replace(":", "/"))
                    suppressObviousFunctions.set(false) // For the FTC rookies
                    suppressInheritedMembers.set(false) // For the FTC rookies
                    includes.from("README.md")
                }
            }
        }
    }
}

tasks {
    withType<org.jetbrains.dokka.gradle.DokkaMultiModuleTask>().configureEach {
        moduleName.set("Unified Robotics")
    }
}

project.ext["extJvmTarget"] = "11"
project.ext["extSourceCompatibility"] = JavaVersion.VERSION_11
project.ext["extTargetCompatibility"] = JavaVersion.VERSION_11
