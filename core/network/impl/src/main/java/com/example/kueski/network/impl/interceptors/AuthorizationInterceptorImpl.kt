package com.example.kueski.network.impl.interceptors

import com.example.kueski.network.api.interceptors.AuthorizationInterceptor
import com.example.kueski.network.api.preferences.NetworkPreferences
import com.example.kueski.network.impl.constants.HttpConstants
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthorizationInterceptorImpl @Inject constructor(
    private val networkPreferences: NetworkPreferences
) : AuthorizationInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val accessToken = networkPreferences.accessToken()
        val requestBuilder = chain.request().newBuilder()

        if (accessToken.isNotEmpty()) {
            requestBuilder.header(
                HttpConstants.AUTHORIZATION,
                "${HttpConstants.BEARER} $accessToken"
            )
        }

        val finalRequest = requestBuilder.build()
        return chain.proceed(finalRequest)
    }
}