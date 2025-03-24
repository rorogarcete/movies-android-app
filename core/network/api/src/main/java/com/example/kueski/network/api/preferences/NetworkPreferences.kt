package com.example.kueski.network.api.preferences

interface NetworkPreferences {
    fun baseMicroService(): String

    fun basePath(): String

    fun baseImagePath(): String

    fun accessToken(): String

    fun setAccessToken(token: String)
}