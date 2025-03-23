package com.example.kueski.logger_impl

import android.util.Log
import com.google.firebase.crashlytics.BuildConfig

object LogUtil {

    private val crashlyticsLogger = CrashlyticsLogger

    fun e(
        tag: String,
        msg: String? = null,
        throwable: Throwable? = null,
        customKeys: List<Pair<String, String>>? = null
    ) {
        doSomethingOnDebug {
            Log.e(tag, msg, throwable)
        }

        crashlyticsLogger.log(
            tag,
            msg.orEmpty(),
            Log.ERROR,
            throwable = throwable,
            customKeys = customKeys
        )
    }

    fun i(tag: String, msg: String) {
        doSomethingOnDebug {
            Log.i(tag, msg)
        }

        crashlyticsLogger.log(tag, msg, Log.INFO)
    }

    fun w(tag: String, msg: String) {
        doSomethingOnDebug {
            Log.w(tag, msg)
        }

        crashlyticsLogger.log(tag, msg, Log.WARN)
    }

    fun d(tag: String, msg: String?, throwable: Throwable? = null) {
        doSomethingOnDebug {
            Log.d(tag, msg, throwable)
        }
    }

    private fun <T> doSomethingOnDebug(bodyFunction: () -> T) {
        if (BuildConfig.DEBUG) {
            bodyFunction()
        }
    }
}
