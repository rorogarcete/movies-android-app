package com.example.kueski.network.impl.interceptors

import com.example.kueski.network.api.interceptors.ApiServicesUrlInterceptor
import com.example.kueski.network.api.preferences.NetworkPreferences
import javax.inject.Inject

class ApiServicesUrlInterceptorImpl @Inject constructor(
    private val networkPreferences: NetworkPreferences
) : BaseUrlInterceptor, ApiServicesUrlInterceptor {

    override fun getPath() = networkPreferences.baseApiService()
}