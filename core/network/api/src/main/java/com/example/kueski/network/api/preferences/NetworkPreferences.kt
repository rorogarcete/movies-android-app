package com.example.kueski.network.api.preferences

interface NetworkPreferences {
    fun baseApiService(): String

    fun accessToken(): String
}