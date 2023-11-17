plugins {
    id("application")
    id("org.jetbrains.dokka") version "1.9.0"
    kotlin("jvm")
}

application {
    mainClass.set("org.ironlions.sovereign.panopticon.client.ClientKt")
    applicationDefaultJvmArgs = listOf("-XstartOnFirstThread")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_17
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
            "-opt-in=kotlin.RequiresOptIn",
            "-Xallow-kotlin-package"
        )
    }
}

val lwjglVersion = "3.3.3"

val lwjglNatives = Pair(
    System.getProperty("os.name")!!,
    System.getProperty("os.arch")!!
).let { (name, arch) ->
    when {
        arrayOf("Linux", "FreeBSD", "SunOS", "Unit").any { name.startsWith(it) } ->
            if (arrayOf("arm", "aarch64").any { arch.startsWith(it) })
                "natives-linux${if (arch.contains("64") || arch.startsWith("armv8")) "-arm64" else "-arm32"}"
            else if (arch.startsWith("ppc"))
                "natives-linux-ppc64le"
            else if (arch.startsWith("riscv"))
                "natives-linux-riscv64"
            else
                "natives-linux"

        arrayOf("Mac OS X", "Darwin").any { name.startsWith(it) } ->
            "natives-macos${if (arch.startsWith("aarch64")) "-arm64" else ""}"

        arrayOf("Windows").any { name.startsWith(it) } ->
            if (arch.contains("64"))
                "natives-windows${if (arch.startsWith("aarch64")) "-arm64" else ""}"
            else
                "natives-windows-x86"

        else -> throw Error("Unrecognized or unsupported platform. Please set \"lwjglNatives\" manually")
    }
}

dependencies {
    /* Kotlin */
    implementation(kotlin("stdlib-jdk8"))
    implementation(kotlin("reflect"))

    /* Misc */
    implementation("io.github.oshai", "kotlin-logging-jvm", "5.1.0")
    implementation("org.slf4j", "slf4j-reload4j", "2.0.9")

    /* GUI 2D */
    //noinspection GradleDynamicVersion
    implementation("kotlin.graphics", "imgui-core", "1.79+05")
    //noinspection GradleDynamicVersion
    implementation("kotlin.graphics", "imgui-gl", "1.79+05")
    //noinspection GradleDynamicVersion
    implementation("kotlin.graphics", "imgui-glfw", "1.79+05")
    implementation("kotlin.graphics", "uno", "0.7.21")
    implementation("kotlin.graphics", "kool", "0.9.79")

    /* GUI 3D */
    implementation(platform("org.lwjgl:lwjgl-bom:$lwjglVersion"))
    implementation("org.lwjgl", "lwjgl")
    implementation("org.lwjgl", "lwjgl-remotery")
    implementation("org.lwjgl", "lwjgl-opengl")
    implementation("org.lwjgl", "lwjgl-glfw")
    implementation("org.lwjgl", "lwjgl-jemalloc")
    implementation("org.lwjgl", "lwjgl-stb")
    runtimeOnly("org.lwjgl", "lwjgl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-remotery", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-jemalloc", classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", classifier = lwjglNatives)
}
