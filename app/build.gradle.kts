plugins {
  id("com.android.application")
  id("dagger.hilt.android.plugin")
  id("org.jetbrains.kotlin.plugin.compose") version "2.0.0"
  kotlin("android")
  kotlin("plugin.parcelize")
  kotlin("kapt")
}

android {
  compileSdk = 35
  namespace = "com.example.kueski_movies"

  defaultConfig {
    applicationId = "com.example.kueski_movies"
    minSdk = 26
    targetSdk = 35
    versionCode = 2
    versionName = "1.0.0"

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    buildConfigField("String", "IMG_URL", "\"https://image.tmdb.org/t/p/original\"")
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = false
      isDebuggable = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      buildConfigField("boolean", "APP_LOGS_FLAG", "true")
    }
    named("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      buildConfigField("boolean", "APP_LOGS_FLAG", "false")
    }
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  buildFeatures {
    buildConfig = true
    viewBinding = true
    compose = true
  }
}

dependencies {
  implementation(libs.androidx.core.core.ktx)
  implementation(libs.appcompat)

  // Retrofit
  implementation(libs.retrofit)
  implementation(libs.converter.gson)
  implementation(libs.logging.interceptor)

  // Dagger Hilt
  implementation(libs.hilt.android)
  kapt(libs.hilt.android.compiler)
  implementation(libs.androidx.hilt.navigation.compose)

  // Lottie
  implementation(libs.lottie.compose)

  // Room
  implementation(libs.room.ktx)
  implementation(libs.room.paging)
  implementation(libs.room.runtime)
  kapt(libs.room.compiler)

  // Compose
  implementation(libs.activity.compose)
  implementation(libs.material3)
  implementation(libs.androidx.lifecycle.viewmodel.compose)
  implementation(libs.androidx.paging.compose)
  implementation(libs.coil.compose)
  debugImplementation(libs.ui.tooling)

  // Internals libs
  implementation(project(":logger-api"))
  implementation(project(":logger-impl"))

  implementation(project(":feature-flag-api"))
  implementation(project(":feature-flag-impl"))

  implementation(project(":network-api"))
  implementation(project(":network-impl"))

  // Timber Logs
  implementation(libs.timber)

  // Testing
  testImplementation(libs.junit.junit)
  testImplementation(libs.mockito.inline)
  testImplementation(libs.mockito.core)
  testImplementation(libs.kotlinx.coroutines.test)
  testImplementation(libs.androidx.core.testing)
  testImplementation(libs.mockwebserver)

  androidTestImplementation(libs.espresso.core)
  androidTestImplementation(libs.mockito.android)
  androidTestImplementation(libs.androidx.ui.test.junit4.android)
  debugImplementation(libs.ui.test.manifest)
}