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
    }
}

rootProject.name = "sovereign"
include(":sovereign")
include(":panopticon")