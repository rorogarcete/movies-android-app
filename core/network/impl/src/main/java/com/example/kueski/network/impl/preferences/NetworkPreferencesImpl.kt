package com.example.kueski.network.impl.preferences

import android.content.Context
import com.example.kueski.network.api.preferences.NetworkPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NetworkPreferencesImpl @Inject constructor(
    @ApplicationContext context: Context
) : NetworkPreferences, PreferencesBase() {

    init {
        super.init(context, NETWORK_PREFERENCE_FILE)
    }

    override fun baseApiService(): String {
        return getOr(API_SERVICES, API_SERVICES_URL)
    }

    override fun basePath(): String {
        return getOr(BASE_PATH, BASE_PATH_URL)
    }

    override fun accessToken(): String {
        return getOr(ACCESS_TOKEN, "")
    }

    override fun baseImagePath(): String {
        return getOr(BASE_PATH, BASE_PATH_URL)
    }

    override fun setAccessToken(token: String) {
        set(ACCESS_TOKEN, token)
    }

    companion object {
        private const val NETWORK_PREFERENCE_FILE = "NetworkSettings"

        private const val API_SERVICES = "apiServices"
        private const val API_SERVICES_URL = "https://api.themoviedb.org/"

        private const val BASE_PATH = "basePath"
        private const val BASE_PATH_URL = "https://image.tmdb.org/t/p/original"

        private const val ACCESS_TOKEN = "accessToken"
    }
}