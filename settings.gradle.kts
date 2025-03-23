pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}
rootProject.name = "movies-android-app"
include(":app")

apply { from("$rootDir/core/core-settings.gradle") }
