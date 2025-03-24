package com.example.kueski.network.impl.models

import com.example.kueski.network.impl.constants.HttpConstants
import com.example.kueski.network.impl.constants.HttpConstants.CODE_KEY
import com.example.kueski.network.impl.constants.HttpConstants.DETAIL_KEY
import com.example.kueski.network.impl.constants.HttpConstants.ERRORS_KEY
import com.example.kueski.network.impl.constants.HttpConstants.ERROR_KEY
import com.example.kueski.network.impl.constants.HttpConstants.MESSAGE_KEY
import com.example.kueski.network.impl.constants.HttpConstants.SEVERITY_KEY
import com.example.kueski.network.impl.constants.HttpConstants.URL_KEY
import com.example.kueski.network.impl.extensions.getJsonObjectOrDefault
import com.example.kueski.network.impl.extensions.tryOrDefault
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

data class ServerException internal constructor(
    override val message: String = "",
    val response: Response<*>?,
    val kind: Kind,
    val exception: Throwable?,
    val serverErrorResponse: ServerErrorResponse? = null,
    val url: String = "",
    val code: String = "",
    val severity: String? = null,
    val rawError: String? = null,
    val status: ServiceStatus = ServiceStatus.UNDEFINED
) : RuntimeException(message, exception)

/** Identifies the event kind which triggered a [ServerException].  */
enum class Kind {
    /** An [IOException] occurred while communicating to the server.  */
    NETWORK,

    /** A non-200 HTTP status code was received from the server.  */
    HTTP,

    /**
     * An internal error occurred while attempting to execute a request. It is best practice to
     * re-throw this exception so your application crashes.
     */
    UNEXPECTED
}

fun httpError(
    url: String,
    response: Response<*>,
    genericError: String,
): ServerException {
    val serverErrorBody = getErrorMessage(response)
    val serverCode = response.code().toString()
    val serverErrorResponse = getReadableError(serverErrorBody, genericError, serverCode)

    return ServerException(
        serverErrorResponse.message,
        response,
        Kind.HTTP,
        null,
        serverErrorResponse,
        url,
        serverErrorResponse.code,
        serverErrorResponse.severity,
        serverErrorBody.toString(),
        ServiceStatus.HTTP_ERROR
    )
}

fun getErrorBody(serverErrorBody: JsonObject): JsonObject? {
    return serverErrorBody.getJsonObjectOrDefault(ERRORS_KEY)
        ?: serverErrorBody.getJsonObjectOrDefault(ERROR_KEY)
}

fun getErrorMessage(response: Response<*>): JsonObject {
    return tryOrDefault(
        {
            Gson().fromJson(response.errorBody()?.string(), JsonObject::class.java)
        },
        JsonObject()
    )
}

fun networkError(
    exception: IOException,
    networkError: String,
) = ServerException(
    networkError,
    null,
    Kind.NETWORK,
    exception,
    status = ServiceStatus.NOT_INTERNET_CONNECTION
)

fun unknownHostError(
    unknownHostException: UnknownHostException,
    networkError: String,
    url: String? = null
) = ServerException(
    networkError,
    null,
    Kind.NETWORK,
    unknownHostException,
    url = url.orEmpty(),
    status = ServiceStatus.HOST_UNRESOLVED
)

fun timeoutError(
    socketTimeoutException: SocketTimeoutException,
    networkError: String,
    url: String? = null
) = ServerException(
    networkError,
    null,
    Kind.NETWORK,
    socketTimeoutException,
    url = url.orEmpty(),
    status = ServiceStatus.TIMEOUT
)

fun unexpectedError(
    exception: Throwable
) = ServerException(
    exception.message.orEmpty(),
    null,
    Kind.UNEXPECTED,
    exception,
    status = ServiceStatus.UNDEFINED
)

@Suppress("LongMethod")
fun getReadableError(
    jsonResponse: JsonObject,
    genericError: String,
    serverCode: String
): ServerErrorResponse {

    var message = genericError
    var severity = ""
    var code = ""
    var url = ""

    try {
        val jsonObject = JSONObject(jsonResponse.toString())
        var jsonError: JSONObject? = null
        when {
            hasJsonFormat(
                jsonObject,
                ERROR_KEY
            ) -> jsonError = jsonObject.getJSONObject(ERROR_KEY)
            hasJsonFormat(
                jsonObject,
                ERRORS_KEY
            ) -> jsonError = jsonObject.getJSONObject(ERRORS_KEY)
            jsonObject.has(ERRORS_KEY) -> jsonError = jsonObject.getJSONObject(
                ERRORS_KEY
            )
            jsonObject.has(URL_KEY) -> jsonError = jsonObject
            jsonObject.has(MESSAGE_KEY) -> jsonError = jsonObject
        }

        jsonError?.let {
            if (it.has(MESSAGE_KEY)) {
                message = jsonError.getString(HttpConstants.MESSAGE_KEY)
            }

            if (it.has(DETAIL_KEY)) {
                message += ". " + it.getJSONObject(DETAIL_KEY).getString(
                    MESSAGE_KEY
                )
            }

            if (it.has(SEVERITY_KEY)) {
                severity = jsonError.getString(SEVERITY_KEY)
            }

            if (it.has(CODE_KEY)) {
                code = it.getString(CODE_KEY)
            }

            if (it.has(URL_KEY)) {
                url = it.getString(URL_KEY)
            }
        }
        return ServerErrorResponse(
            message,
            code,
            severity,
            url,
            serverCode = serverCode
        )
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    return ServerErrorResponse(
        genericError
    )
}

fun hasJsonFormat(json: JSONObject, key: String): Boolean {
    return json.has(key) && isJSONValid(json.getString(key))
}

internal fun isJSONValid(test: String): Boolean {
    try {
        JSONObject(test)
    } catch (ex: JSONException) {
        try {
            JSONArray(test)
        } catch (ex1: JSONException) {
            return false
        }
    }
    return true
}
