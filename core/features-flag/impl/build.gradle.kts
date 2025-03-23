plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    namespace = "com.example.kueski.feature_flag_impl"
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
    implementation(project(":feature-flag-api"))
    implementation(project(":logger-api"))

    // Firebase
    implementation(libs.firebase.core)
    implementation(libs.firebase.iid)
    implementation(libs.firebase.config)

    implementation(libs.gson)
    implementation(libs.dagger)
}