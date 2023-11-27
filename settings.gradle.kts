pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.brott.dev")
        maven("https://raw.githubusercontent.com/kotlin-graphics/mary/master")
        maven("https://maven.realrobotix.me/imgui-java")
    }
}

rootProject.name = "sovereign"
include(":sovereign")
include(":panopticon")
include(":misc:common")