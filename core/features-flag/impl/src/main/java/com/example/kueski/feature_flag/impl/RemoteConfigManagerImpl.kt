package com.example.kueski.feature_flag.impl

import com.example.kueski.feature_flag.api.RemoteConfigDefaults
import com.example.kueski.feature_flag.api.RemoteConfigManager
import com.example.kueski.logger_api.Logger
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.gson.Gson
import dagger.Lazy
import javax.inject.Inject

class RemoteConfigManagerImpl @Inject constructor(
    private val remoteConfig: Lazy<FirebaseRemoteConfig>,
    @RemoteConfigDefaults
    defaultParams: @JvmSuppressWildcards Map<String, Any>,
    private val gson: Gson,
    private val logger: Logger
) : RemoteConfigManager {

    init {
        setRemoteConfig()
        setupDefaultValues(defaultParams)
        FirebaseInstanceId.getInstance().token?.let {
            logger.i(FIREBASE_TOKEN, it)
        }
    }

    private fun setRemoteConfig() {
        remoteConfig.get().setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .apply {
                    minimumFetchIntervalInSeconds = 0
                }.build()
        )
    }

    private fun setupDefaultValues(defaultParams: Map<String, Any>) {
        remoteConfig.get().setDefaultsAsync(defaultParams)
    }

    override fun getValue(key: String) =
        KueskiRemoteConfigValue(remoteConfig.get().getValue(key), gson)

    companion object {
        private const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
    }
}