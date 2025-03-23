package com.example.kueski.feature_flag.impl

import com.example.kueski.feature_flag.api.RemoteConfigValue
import com.example.kueski.feature_flag.impl.extensions.tryOrDefault
import com.example.kueski.feature_flag.impl.extensions.tryOrDefaultWithOnError
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class KueskiRemoteConfigValue(
    private val value: FirebaseRemoteConfigValue,
    private val gson: Gson
) : RemoteConfigValue {

    override fun asLong(default: Long): Long = tryOrDefault({ value.asLong() }, default)

    override fun asDouble(): Double = value.asDouble()

    override fun asString(): String = value.asString()

    override fun asByteArray(): ByteArray = value.asByteArray()

    override fun asBoolean(default: Boolean): Boolean {
        return tryOrDefault(
            {
                if (value.asString().isBlank()) {
                    default
                } else {
                    value.asBoolean()
                }
            },
            default
        )
    }

    override fun <T> asJson(clazz: Class<T>): T? {
        return tryOrDefault(
            {
                gson.fromJson<T>(value.asString(), clazz)
            },
            null
        )
    }

    override fun <T> asJsonList(onError: (throwable: Throwable) -> Unit): List<T> {
        return tryOrDefaultWithOnError(
            {
                gson.fromJson(value.asString(), object : TypeToken<ArrayList<T>>() {}.type)
            }, {
                onError(it)
                emptyList()
            }
        )
    }
}