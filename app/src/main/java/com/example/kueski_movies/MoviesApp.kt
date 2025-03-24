package com.example.kueski_movies

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initFirebase(this)
        initCrashlytics()
    }

    private fun initFirebase(context: Context) {
        if (!BuildConfig.DEBUG) {
            val firebaseOptions = FirebaseOptions.Builder()
                .setApplicationId("your_applicationId")
                .setApiKey("you_firebaseApiKey")
                .setDatabaseUrl("FirebaseDatabaseUrl")
                .setGcmSenderId("GcmSenderId")
                .setStorageBucket("FirebaseStorageBucket")
                .setProjectId("FirebaseProjectId")
                .build()
            FirebaseApp.initializeApp(context, firebaseOptions)
        }
    }

    private fun initCrashlytics() {
        if (!BuildConfig.DEBUG) {
            FirebaseCrashlytics.getInstance().isCrashlyticsCollectionEnabled = BuildConfig.ANALYTICS_FLAG
        }
    }
}