package com.example.kueski.network.api.analytics

interface NetworkErrorAnalytics {
    fun log(
        event: String,
        params: Map<String, String> = mapOf()
    )
}