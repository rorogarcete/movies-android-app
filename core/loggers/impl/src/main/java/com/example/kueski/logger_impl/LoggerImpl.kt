package com.example.kueski.logger_impl

import com.example.kueski.logger_api.Logger
import javax.inject.Inject

class LoggerImpl @Inject constructor(
    private val logUtil: LogUtil
) : Logger {
    override fun e(tag: String, msg: String?, throwable: Throwable?, customKeys: List<Pair<String, String>>?) {
        logUtil.e(tag = tag, msg = msg, throwable = throwable, customKeys = customKeys)
    }

    override fun i(tag: String, msg: String) {
        logUtil.i(tag, msg)
    }

    override fun w(tag: String, msg: String) {
        logUtil.w(tag, msg)
    }

    override fun d(tag: String, msg: String?, throwable: Throwable?) {
        if (throwable != null) {
            logUtil.d(tag, msg, throwable)
        } else {
            logUtil.d(tag, msg)
        }
    }
}
