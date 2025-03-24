import java.util.Properties

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.example.kueski.network_impl"
    compileSdk = 35

    defaultConfig {
        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val accessToken = properties.getProperty("ACCESS_TOKEN") ?: ""
        buildConfigField("String", "ACCESS_TOKEN", "\"$accessToken\"")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":network-api"))
    implementation(project(":logger-api"))

    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.gson)

    implementation(libs.securePreferences)

    implementation(libs.amplitude)

    // Dagger Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}