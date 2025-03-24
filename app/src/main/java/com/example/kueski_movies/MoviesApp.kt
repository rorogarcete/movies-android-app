package com.example.kueski_movies

import android.app.Application
import com.example.kueski.network.api.preferences.NetworkPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MoviesApp : Application() {

    @Inject
    lateinit var networkPreferences: NetworkPreferences

    override fun onCreate() {
        super.onCreate()
        val token = System.getenv("AUTH_TOKEN").orEmpty()
        networkPreferences.setAccessToken(token)
    }
}