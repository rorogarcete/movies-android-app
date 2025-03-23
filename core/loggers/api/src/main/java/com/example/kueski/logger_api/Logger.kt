package com.example.kueski.logger_api

interface Logger {

    fun e(
        tag: String,
        msg: String? = null,
        throwable: Throwable? = null,
        customKeys: List<Pair<String, String>>? = null
    )

    fun i(tag: String, msg: String)

    fun w(tag: String, msg: String)

    fun d(tag: String, msg: String? = null, throwable: Throwable? = null)
}