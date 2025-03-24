package com.example.kueski.network.impl.models

data class NetworkAnalytics(
    val errorBody: String,
    val errorCode: String,
    val httpStatusCode: String,
    val url: String
)
