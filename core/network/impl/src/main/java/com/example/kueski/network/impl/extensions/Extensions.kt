package com.example.kueski.network.impl.extensions

import com.google.gson.JsonElement
import com.google.gson.JsonObject

fun JsonObject?.getJsonObjectOrDefault(
    key: String,
    defaultValue: JsonObject? = null
): JsonObject? =
    tryOrDefault({
        getJsonElementOrDefault(key)?.asJsonObject ?: defaultValue
    }, defaultValue)

fun JsonObject?.getJsonElementOrDefault(
    key: String,
    defaultValue: JsonElement? = null
): JsonElement? =
    tryOrDefault({
        if (this?.get(key)?.isJsonNull == false) {
            this.get(key) ?: defaultValue
        } else {
            defaultValue
        }
    }, defaultValue)

/**
 * Lambda that return the result of the [f] lambda or the [defaultValue]
 * @param f lambda to invoke
 * @param defaultValue value tu return in case of an exception while invoking [f]
 */
internal inline fun <T> tryOrDefault(
    f: () -> T,
    defaultValue: T
): T {
    return try {
        f()
    } catch (e: Exception) {
        defaultValue
    }
}