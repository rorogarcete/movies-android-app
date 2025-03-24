package com.example.kueski.network.impl.di

import com.example.kueski.network.api.interceptors.ApiServicesUrlInterceptor
import com.example.kueski.network.api.interceptors.AuthorizationInterceptor
import com.example.kueski.network.api.interceptors.ConnectionTimeOutInterceptor
import com.example.kueski.network.api.interceptors.HttpRetryInterceptor
import com.example.kueski.network.impl.interceptors.ApiServicesUrlInterceptorImpl
import com.example.kueski.network.impl.interceptors.AuthorizationInterceptorImpl
import com.example.kueski.network.impl.interceptors.ConnectionTimeOutInterceptorImpl
import com.example.kueski.network.impl.interceptors.HttpRetryInterceptorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InterceptorModule {

    @Binds
    fun bindAuthorizationInterceptor(
        authorizationInterceptor: AuthorizationInterceptorImpl
    ): AuthorizationInterceptor


    @Binds
    fun bindHttpRetryInterceptor(
        httpRetryInterceptor: HttpRetryInterceptorImpl
    ): HttpRetryInterceptor

    @Binds
    fun bindConnectionTimeoutInterceptor(
        connectionTimeOutInterceptor: ConnectionTimeOutInterceptorImpl
    ): ConnectionTimeOutInterceptor

    @Binds
    fun bindMicroServiceUrlInterceptor(
        apiServicesUrlInterceptorImpl: ApiServicesUrlInterceptorImpl
    ): ApiServicesUrlInterceptor
}