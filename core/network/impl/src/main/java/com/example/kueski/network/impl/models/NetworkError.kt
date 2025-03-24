package com.example.kueski.network.impl.models

import retrofit2.Response

data class NetworkError(
    override val message: String = "",
    val exception: Throwable?,
    val url: String = "",
    val response: Response<*>? = null,
    val isConnectionError: Boolean = false,
    val status: ServiceStatus = ServiceStatus.UNDEFINED
) : RuntimeException(message, exception) {

    fun code(): Int = response?.code() ?: -1

    fun getNetworkErrorState(): NetworkErrorUiState {
        return if (isConnectionError) {
            NetworkErrorUiState.NetworkConnection(status)
        } else {
            val errorCode = code()
            NetworkErrorUiState.Network(errorCode)
        }
    }
}

sealed class NetworkErrorUiState {
    /**
     * When is a network error
     * @param code network error code.
     */
    class Network(
        val code: Int
    ) : NetworkErrorUiState()

    /**
     * When is network connection error
     * @param status network error status.
     */
    class NetworkConnection(
        val status: ServiceStatus
    ) : NetworkErrorUiState()
}