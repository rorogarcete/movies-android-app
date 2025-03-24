package com.example.kueski.network.impl.interceptors

import com.example.kueski.network.api.interceptors.MicroServicesUrlInterceptor
import com.example.kueski.network.api.preferences.NetworkPreferences
import javax.inject.Inject

class MicroServicesUrlInterceptorImpl @Inject constructor(
    private val networkPreferences: NetworkPreferences
) : BaseUrlInterceptor, MicroServicesUrlInterceptor {

    override fun getPath() = networkPreferences.baseMicroService()
}