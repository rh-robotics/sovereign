pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

rootProject.name = "unified"
include(":android:sovereign")
include(":common:common")
include(":native:marsh")
include(":native:panopticon")