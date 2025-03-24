package com.example.kueski.network.impl.models

data class ServerErrorResponse(
    val message: String,
    val code: String = "",
    val severity: String = "",
    val url: String = "",
    val ignoreAuth: Boolean = false,
    val serverCode: String = "-1"
)
