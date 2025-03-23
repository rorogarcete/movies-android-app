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
    buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
    buildConfigField("String", "IMG_URL", "\"https://image.tmdb.org/t/p/original\"")
  }

  buildTypes {
    named("release") {
      isMinifyEnabled = false
      isDebuggable = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      buildConfigField(
        "String",
        "MOVIE_API_KEY",
        "\"2805b177bde136e05a6502ec65f855f0\""
      )
      buildConfigField("boolean", "APP_LOGS_FLAG", "true")
    }
    named("debug") {
      isMinifyEnabled = false
      isDebuggable = true
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
      buildConfigField(
        "String",
        "MOVIE_API_KEY",
        "\"2805b177bde136e05a6502ec65f855f0\""
      )
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
  val ACTIVITY_COMPOSE = "1.9.0"
  val ANDROID_CORE = "1.13.1"
  val APP_COMPAT = "1.7.0"
  val ARCH_CORE = "2.2.0"
  val COIL_COMPOSE = "2.7.0"
  val COMPOSE_TEST_MANIFEST = "1.7.8"
  val COROUTINES_TEST = "1.8.1"
  val DAGGER_HILT = "2.51.1"
  val DAGGER_HILT_NAV_COMPOSE = "1.2.0"
  val ESPRESSO = "3.5.1"
  val JUNIT = "4.13.2"
  val JUNIT_COMPOSE = "1.7.8"
  val LIFECYCLE = "2.8.1"
  val LOTTIE = "6.4.1"
  val MATERIAL_COMPOSE = "1.0.0"
  val MOCKITO = "5.12.0"
  val MOCKITO_INLINE = "5.2.0"
  val OKHTTP = "4.12.0"
  val PAGING_COMPOSE = "3.3.0"
  val RETROFIT = "2.11.0"
  val ROOM = "2.6.1"
  val TIMBER = "5.0.1"
  val TOOLING_COMPOSE = "1.6.7"

  implementation("androidx.core:core-ktx:${ANDROID_CORE}")
  implementation("androidx.appcompat:appcompat:${APP_COMPAT}")

  // Timber Logs
  implementation("com.jakewharton.timber:timber:${TIMBER}")

  // Retrofit
  implementation("com.squareup.retrofit2:retrofit:${RETROFIT}")
  implementation("com.squareup.retrofit2:converter-gson:${RETROFIT}")
  implementation("com.squareup.okhttp3:logging-interceptor:${OKHTTP}")

  // Dagger Hilt
  implementation("com.google.dagger:hilt-android:${DAGGER_HILT}")
  kapt("com.google.dagger:hilt-android-compiler:${DAGGER_HILT}")
  implementation("androidx.hilt:hilt-navigation-compose:${DAGGER_HILT_NAV_COMPOSE}")

  // Lottie
  implementation("com.airbnb.android:lottie-compose:${LOTTIE}")

  // Room
  implementation("androidx.room:room-ktx:${ROOM}")
  implementation("androidx.room:room-paging:${ROOM}")
  implementation("androidx.room:room-runtime:${ROOM}")
  kapt("androidx.room:room-compiler:${ROOM}")

  // Compose
  implementation("androidx.activity:activity-compose:${ACTIVITY_COMPOSE}")
  implementation("androidx.compose.material3:material3:${MATERIAL_COMPOSE}")
  implementation("androidx.lifecycle:lifecycle-viewmodel-compose:${LIFECYCLE}")
  implementation("androidx.compose.material3:material3:${MATERIAL_COMPOSE}")
  implementation("androidx.paging:paging-compose:${PAGING_COMPOSE}")
  implementation("io.coil-kt:coil-compose:${COIL_COMPOSE}")
  debugImplementation("androidx.compose.ui:ui-tooling:${TOOLING_COMPOSE}")

  // Internals libs
  implementation(project(":logger-api"))
  implementation(project(":logger-impl"))

  // Testing
  testImplementation("junit:junit:${JUNIT}")
  testImplementation("org.mockito:mockito-inline:${MOCKITO_INLINE}")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:${COROUTINES_TEST}")
  androidTestImplementation("androidx.test.espresso:espresso-core:${ESPRESSO}")
  androidTestImplementation("org.mockito:mockito-android:${MOCKITO}")
  androidTestImplementation("androidx.compose.ui:ui-test-junit4-android:${JUNIT_COMPOSE}")
  debugImplementation("androidx.compose.ui:ui-test-manifest:${COMPOSE_TEST_MANIFEST}")
  api("org.mockito:mockito-core:${MOCKITO}")
  api("androidx.arch.core:core-testing:${ARCH_CORE}")
  api("com.squareup.okhttp3:mockwebserver:${OKHTTP}")
}