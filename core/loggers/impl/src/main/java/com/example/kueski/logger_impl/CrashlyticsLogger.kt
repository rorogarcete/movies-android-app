package com.example.kueski.logger_impl

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics

object CrashlyticsLogger {

    private const val TAG = "TAG"
    private const val INFO = "INFO:/"
    private const val ERROR = "ERROR:/"
    private const val WARN = "WARN:/"
    private const val DEBUG = "DEBUG:/"
    private const val VERBOSE = "VERBOSE:/"

    private val crashlytics by lazy { FirebaseCrashlytics.getInstance() }

    /**
     * Logs message to crashlytics console through crashlytics SDK, only works when the ANALYTICS_FLAG is enabled
     * @param tag represent the tag of the specific message will be logged as custom key
     * @param msg represent the actual message to log
     * @param priority represent the priority level of the message
     * @param customKeys any custom keys and value additional to message
     * @param throwable an exception wanted to be logged as non fatal error
     */
    fun log(
        tag: String,
        msg: String,
        priority: Int = Log.INFO,
        customKeys: List<Pair<String, String>>? = null,
        throwable: Throwable? = null
    ) {
        if (true) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

            crashlytics.setCustomKey(TAG, tag)
            customKeys?.forEach { event -> crashlytics.setCustomKey(event.first, event.second) }
            crashlytics.log("${priority.asString()} $tag $msg")
            throwable?.let {
                crashlytics.recordException(throwable)
            }
        }
    }

    private fun Int.asString(): String {
        return when (this) {
            Log.INFO -> INFO
            Log.ERROR -> ERROR
            Log.WARN -> WARN
            Log.DEBUG -> DEBUG
            Log.VERBOSE -> VERBOSE
            else -> INFO
        }
    }
}
