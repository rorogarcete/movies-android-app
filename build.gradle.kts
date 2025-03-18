apply(plugin = "com.github.ben-manes.versions")

buildscript {
  repositories {
    google()
    mavenCentral()
  }
  dependencies {
    classpath("com.android.tools.build:gradle:8.6.1")
    classpath("com.github.ben-manes:gradle-versions-plugin:0.51.0")
    classpath("com.google.dagger:hilt-android-gradle-plugin:2.51.1")
  }
}

plugins {
  id("org.jetbrains.kotlin.jvm") version("2.0.0")
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}