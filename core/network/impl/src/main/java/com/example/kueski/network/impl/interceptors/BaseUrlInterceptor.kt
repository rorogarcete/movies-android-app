package com.example.kueski.network.impl.interceptors

import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

interface BaseUrlInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val host = getPath().toHttpUrlOrNull()?.toUrl()?.toURI()?.host

        var request = chain.request()

        val newUrl = host?.let {
            request.url.newBuilder()
                .host(host)
                .build()
        } ?: request.url

        request = request.newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(request)
    }

    fun getPath(): String
}