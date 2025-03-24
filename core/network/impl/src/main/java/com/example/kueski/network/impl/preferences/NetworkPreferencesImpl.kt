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

    override fun baseApiService() = getOr(API_SERVICES, API_SERVICES_URL)

    override fun accessToken() = getOr(ACCESS_TOKEN, "")


    override fun setAccessToken(token: String) {
        set(ACCESS_TOKEN, token)
    }

    companion object {
        private const val NETWORK_PREFERENCE_FILE = "NetworkSettings"

        private const val API_SERVICES = "apiServices"
        private const val API_SERVICES_URL = "https://api.themoviedb.org/"

        private const val ACCESS_TOKEN = "accessToken"
    }
}