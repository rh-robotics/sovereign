@Suppress("DSL_SCOPE_VIOLATION") // https://github.com/gradle/gradle/issues/22797#issuecomment-1385330558
plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.multiplatform)
    id("application")
}

val extJvmTarget: String by project
val extSourceCompatibility: JavaVersion by project
val extTargetCompatibility: JavaVersion by project

val lwjglNatives = Pair(
    System.getProperty("os.name")!!, System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf(
            "Linux",
            "FreeBSD",
            "SunOS",
            "Unit"
        ).any { name.startsWith(it) } -> if (arrayOf(
                "arm",
                "aarch64"
            ).any { arch.startsWith(it) }
        ) "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
        else if (arch.startsWith("ppc")) "natives-linux-ppc64le"
        else if (arch.startsWith("riscv")) "natives-linux-riscv64"
        else "natives-linux"

        arrayOf(
            "Mac OS X",
            "Darwin"
        ).any { name.startsWith(it) } -> "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"

        arrayOf("Windows").any { name.startsWith(it) } -> if (arch.contains("64")) "natives-windows${
            if (arch.startsWith(
                    "aarch64"
                )
            ) "-arm64" else ""
        }"
        else "natives-windows-x86"

        else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}

kotlin {
    jvm {
        withJava()

        compilations.all {
            kotlinOptions {
                jvmTarget = jvmTarget
            }
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                /* Kotlin */
                implementation(kotlin("stdlib-jdk8"))
                implementation(kotlin("reflect"))
                implementation(libs.kotlinx.coroutines)

                /* Misc */
                implementation(libs.logging.jvm)
                implementation(libs.reload4j)

                /* Graphics */
                implementation(libs.imgui)
                implementation(libs.imgui.lwjgl3)
                implementation(libs.imgui.natives)
                implementation(libs.glm)
                implementation(libs.kool)

                /* Graphics (LWJGL) */
                implementation(platform("org.lwjgl:lwjgl:${libs.versions.lwjgl}"))
                implementation(libs.lwjgl)
                implementation(libs.lwjgl.remotery)
                implementation(libs.lwjgl.opengl)
                implementation(libs.lwjgl.glfw)
                implementation(libs.lwjgl.jemalloc)
                implementation(libs.lwjgl.stb)

                /* Common */
                implementation(project(":common:common"))
                implementation(project(":native:marsh"))
            }
        }
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = extJvmTarget
}

java {
    sourceCompatibility = extSourceCompatibility
    targetCompatibility = extTargetCompatibility
}

application {
    mainClass.set("org.ironlions.sovereign.panopticon.client.ClientKt")
    applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            moduleName.set("Panopticon")
            suppressObviousFunctions.set(false) // For the FTC rookies
            suppressInheritedMembers.set(false) // For the FTC rookies
            includes.from(project.files(), "overview.md")
        }
    }
}

tasks {
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask<*>>().configureEach {
        compilerOptions.freeCompilerArgs.addAll(
            "-opt-in=kotlin.RequiresOptIn", "-Xallow-kotlin-package"
        )
    }
}