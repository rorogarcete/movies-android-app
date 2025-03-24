package com.example.kueski.network.impl.analytics

import com.amplitude.api.AmplitudeClient
import com.example.kueski.logger_api.Logger
import com.example.kueski.network.api.analytics.NetworkErrorAnalytics
import org.json.JSONObject
import javax.inject.Inject

class NetworkErrorAnalyticsImpl @Inject constructor(
    private val amplitudeClient: AmplitudeClient,
    private val logger: Logger
) : NetworkErrorAnalytics {

    override fun log(event: String, params: Map<String, String>) {
        logger.d(NetworkErrorAnalyticsImpl::class.java.simpleName, "event $event params $params")
        amplitudeClient.logEvent(event, params.toJson())
    }

    private fun Map<String, String>.toJson() = JSONObject().apply {
        try {
            this@toJson.forEach { (key, value) ->
                put(key, value)
            }
        } catch (e: Exception) {
            logger.e(TAG, "error $e")
        }
    }

    private companion object {
        const val TAG = "NetworkErrorAnalytics"
    }
}