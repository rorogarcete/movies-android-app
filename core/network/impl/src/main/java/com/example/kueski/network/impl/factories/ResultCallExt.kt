package com.example.kueski.network.impl.factories

import com.example.kueski.network.api.analytics.NetworkErrorAnalytics
import com.example.kueski.network.impl.models.NetworkAnalytics
import com.example.kueski.network.impl.models.NetworkError
import com.example.kueski.network.impl.models.ServerException

private const val SERVER_ERROR = "SERVER_ERROR"
private const val PARAM_HTTP_STATUS_CODE = "HTTP_STATUS_CODE"
private const val PARAM_SERVER_URL = "SERVER_URL"
private const val PARAM_ERROR_CODE = "ERROR_CODE"
private const val PARAM_ERROR_BODY = "ERROR_BODY"

fun sendAnalytics(
    networkErrorAnalytics: NetworkErrorAnalytics,
    networkError: NetworkError,
    url: String
) {
    sendToAnalytics(networkErrorAnalytics, networkError, url)
}

fun sendAnalytics(
    networkErrorAnalytics: NetworkErrorAnalytics,
    serverException: ServerException,
    url: String
) {
    sendToAnalytics(networkErrorAnalytics, serverException, url)
}

fun sendToAnalytics(
    networkErrorAnalytics: NetworkErrorAnalytics,
    networkError: NetworkError,
    url: String
) {
    networkErrorAnalytics.log(
        SERVER_ERROR,
        buildParams(
            NetworkAnalytics(
                networkError.response?.errorBody().toString(),
                networkError.status.statusName,
                networkError.response?.code().toString(),
                url
            )
        )
    )
}

private fun sendToAnalytics(
    networkErrorAnalytics: NetworkErrorAnalytics,
    serverException: ServerException,
    url: String
) {
    networkErrorAnalytics.log(
        SERVER_ERROR,
        buildParams(
            NetworkAnalytics(
                serverException.response?.errorBody().toString(),
                serverException.status.statusName,
                serverException.response?.code().toString(),
                url
            )
        )
    )
}

private fun buildParams(networkAnalytics: NetworkAnalytics): Map<String, String> = mapOf(
    PARAM_HTTP_STATUS_CODE to networkAnalytics.httpStatusCode,
    PARAM_ERROR_CODE to networkAnalytics.errorCode,
    PARAM_ERROR_BODY to networkAnalytics.errorBody,
    PARAM_SERVER_URL to networkAnalytics.url
)