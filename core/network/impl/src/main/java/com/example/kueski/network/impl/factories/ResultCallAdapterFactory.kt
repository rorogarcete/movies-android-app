package com.example.kueski.network.impl.factories

import com.example.kueski.logger_api.Logger
import com.example.kueski.network.api.analytics.NetworkErrorAnalytics
import com.example.kueski.network.impl.models.NetworkError
import com.example.kueski.network.impl.models.ServiceStatus
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit

class ResultCallAdapterFactory @Inject constructor(
    private val networkErrorAnalytics: NetworkErrorAnalytics,
    private val logger: Logger
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType) {
            return null
        }

        val upperBound = getParameterUpperBound(0, returnType)

        return if (upperBound is ParameterizedType && upperBound.rawType == Result::class.java) {
            object : CallAdapter<Any, Call<Result<*>>> {
                override fun responseType(): Type = getParameterUpperBound(0, upperBound)

                override fun adapt(call: Call<Any>): Call<Result<*>> {
                    val url = (call.request() as Request).url.toString()
                    return ResultCall(
                        call,
                        networkErrorAnalytics,
                        url,
                        logger
                    ) as Call<Result<*>>
                }
            }
        } else {
            val responseType = getParameterUpperBound(0, returnType)
            CoroutineCallAdapter<Any>(
                logger,
                responseType,
                networkErrorAnalytics
            )
        }
    }
}


private class CoroutineCallAdapter<T>(
    private val logger: Logger,
    private val responseType: Type,
    private val networkErrorAnalytics: NetworkErrorAnalytics,
) : CallAdapter<T, Call<T>> {

    override fun responseType(): Type = responseType

    override fun adapt(call: Call<T>): Call<T> {
        return object : Call<T> by call {
            override fun enqueue(callback: Callback<T>) {
                call.enqueue(object : Callback<T> {
                    override fun onResponse(call: Call<T>, response: Response<T>) {
                        if (response.isSuccessful) {
                            logger.d(TAG, "onResponse : ${response.code()}")
                            callback.onResponse(call, response)
                        } else {
                            val url = (call.request() as Request).url.toString()
                            val networkError = getErrorByThrowable(url, HttpException(response))
                            logger.d(TAG, "onResponse exception: $networkError")
                            sendToAnalytics(networkErrorAnalytics, networkError, url)
                            callback.onFailure(call, networkError)
                        }
                    }

                    override fun onFailure(call: Call<T>, throwable: Throwable) {
                        logger.d(TAG, "onFailure: $throwable")
                        val url = (call.request() as Request).url.toString()
                        val networkError = getErrorByThrowable(url, throwable)
                        sendToAnalytics(networkErrorAnalytics, networkError, url)
                        callback.onFailure(call, networkError)
                    }
                })
            }
        }
    }

    private companion object {
        const val TAG = "CoroutineCallAdapter"
    }
}

private class ResultCall<T>(
    val delegate: Call<T>,
    val networkErrorAnalytics: NetworkErrorAnalytics,
    val url: String,
    val logger: Logger
) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(
            object : Callback<T> {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    if (response.isSuccessful) {
                        logger.d(TAG, "onResponse (${response.code()})")
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(
                                response.code(),
                                Result.success(response.body()!!)
                            )
                        )
                    } else {
                        logger.d(TAG, "onResponse error:($response)")
                        val networkError = getErrorByThrowable(url, HttpException(response))
                        sendAnalytics(networkErrorAnalytics, networkError, url)
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(
                                Result.failure(networkError)
                            )
                        )
                    }
                }

                override fun onFailure(call: Call<T>, throwable: Throwable) {
                    logger.d(TAG, "onFailure: $throwable")
                    val networkError = getErrorByThrowable(url, throwable)
                    sendAnalytics(networkErrorAnalytics, networkError, url)
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(networkError))
                    )
                }
            }
        )
    }

    override fun clone(): Call<Result<T>> =
        ResultCall(delegate.clone(), networkErrorAnalytics, url, logger)

    override fun execute(): Response<Result<T>> =
        Response.success(Result.success(delegate.execute().body()!!))

    override fun isExecuted() = delegate.isExecuted

    override fun cancel() = delegate.cancel()

    override fun isCanceled() = delegate.isCanceled

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()

    private companion object {
        const val TAG = "ResultCallAdapter"
    }
}

private fun getErrorByThrowable(url: String, throwable: Throwable): NetworkError {
    val networkError = when (throwable) {
        is HttpException -> {
            val response = throwable.response()
            NetworkError(
                message = "Something went wrong, http error", url = url,
                response = response,
                exception = throwable,
                status = ServiceStatus.HTTP_ERROR
            )
        }

        is ConnectException -> {
            NetworkError(
                message = "Unable to connect to the server", url = url, exception = throwable,
                isConnectionError = true,
                status = ServiceStatus.TIMEOUT
            )
        }

        is SocketTimeoutException -> {
            NetworkError(
                message = "Connection timed out", url = url, exception = throwable,
                isConnectionError = true,
                status = ServiceStatus.TIMEOUT
            )
        }

        is UnknownHostException -> {
            NetworkError(
                message = "Unable to resolve host", url = url, exception = throwable,
                isConnectionError = true,
                status = ServiceStatus.HOST_UNRESOLVED
            )
        }

        is IOException -> {
            NetworkError(
                message = "No internet connection", url = url, exception = throwable,
                isConnectionError = true,
                status = ServiceStatus.NOT_INTERNET_CONNECTION
            )
        }

        else -> {
            NetworkError(
                message = throwable.localizedMessage.orEmpty(), url = url, exception = throwable,
                isConnectionError = true
            )
        }
    }
    return networkError
}

