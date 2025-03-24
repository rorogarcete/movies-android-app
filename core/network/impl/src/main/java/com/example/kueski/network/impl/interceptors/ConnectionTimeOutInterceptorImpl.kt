package com.example.kueski.network.impl.interceptors

import com.example.kueski.network.api.interceptors.ConnectionTimeOutInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Interceptor class responsible for handling timeout values for the OkHTTP clients.
 *
 * **Timeout types:**
 * - Connect Timeout:
 *  Timeout value applied when connecting a TCP Socket to the target host.
 * - Read timeout:
 *  Timeout value applied for both TCP socket and individual read IO operations,
 *  including the Source of the response.
 * - Write timeout:
 *  Timeout value applied for both TCP socket and individual write IO operations,
 *  including the Source of the response.
 */
class ConnectionTimeOutInterceptorImpl @Inject constructor() :
    ConnectionTimeOutInterceptor {

    /**
     * Assigns the timeout values to the interceptor chain.
     */
    override fun intercept(chain: Interceptor.Chain): Response = chain.run {
        val timeout = if (request().method == HTTP_VERB_GET) {
            REGULAR_TIMEOUT
        } else {
            LONG_TIMEOUT
        }

        withConnectTimeout(timeout.first, timeout.second)
            .withReadTimeout(timeout.first, timeout.second)
            .withWriteTimeout(timeout.first, timeout.second)
            .proceed(request())
    }

    internal companion object {
        const val HTTP_VERB_GET = "GET"
        val LONG_TIMEOUT = 1 to TimeUnit.MINUTES
        val REGULAR_TIMEOUT = 3 to TimeUnit.SECONDS
    }
}