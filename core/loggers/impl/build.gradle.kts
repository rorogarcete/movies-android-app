plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.example.kueski.logger_impl"
    compileSdk = 35

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":logger-api"))

    implementation(libs.firebase.crashlytics)
}