package com.example.kueski.network.impl.interceptors

import com.example.kueski.network.api.interceptors.HttpRetryInterceptor
import com.example.kueski.network.impl.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject
import okhttp3.ResponseBody.Companion.toResponseBody

/**
 * Interceptor responsible for retrying a known set of response codes.
 */
@Suppress("MagicNumber")
class HttpRetryInterceptorImpl @Inject constructor() : HttpRetryInterceptor {

    /**
     * Set of know response codes that is safe to make a retry.
     */
    private val targetCodes =
        arrayOf(408, 409, 429, 500, 502, 503, 504, 598, 599)

    /**
     * Set of excluded verbs from retry operations
     */
    private val excludedVerbs =
        arrayOf("POST", "CONNECT")

    // Add here regular expression of any service(method type in excludedVerbs)
    // you need to have exponential retries
    private val whiteListedServicesRegEx = emptyList<Regex>()

    override fun intercept(chain: Interceptor.Chain): Response {
        // Try the request call
        val request = chain.request()
        var response = safeProceed(chain, request)

        if (excludedVerbs.contains(request.method) &&
            isWhiteListService(request.url.encodedPath).not()
        ) {
            return response
        }

        // Retry until get a valid response or retry count has been exceed
        var retryCount = 0
        while (retryCount++ < HttpConstants.MAX_RETRIES && targetCodes.contains(response.code)) {
            // Exponential wait time between retries
            val exponentialSleepTime = Math.round(Math.pow(2.0, retryCount.toDouble()) * HttpConstants.BASE_DELAY)
            try {
                Thread.sleep(exponentialSleepTime)
                response.close()
                response = safeProceed(chain, request)
            } catch (e: InterruptedException) {
                // Restore interrupted status and exit loop
                Thread.currentThread().interrupt()
                break
            }
        }

        return response
    }

    @Throws(IOException::class)
    private fun safeProceed(chain: Interceptor.Chain, request: Request): Response {
        return try {
            chain.proceed(request)
        } catch (e: SocketTimeoutException) {
            Response.Builder().request(request)
                .protocol(Protocol.HTTP_1_1)
                .message("Socket timeout")
                .body(
                    JSONObject().apply {
                        put(HttpConstants.MESSAGE_KEY, "Socket timeout")
                        put(HttpConstants.CODE_KEY, "408")
                    }.toString()
                        .toResponseBody(HttpConstants.APPLICATION_JSON.toMediaTypeOrNull())
                )
                .code(408)
                .build()
        }
    }

    private fun isWhiteListService(path: String) =
        whiteListedServicesRegEx.any { it.matches(path) }
}
