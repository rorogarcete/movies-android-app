package com.example.kueski.network.api.preferences

interface NetworkPreferences {
    fun baseApiService(): String

    fun accessToken(): String

    fun setAccessToken(token: String)
}