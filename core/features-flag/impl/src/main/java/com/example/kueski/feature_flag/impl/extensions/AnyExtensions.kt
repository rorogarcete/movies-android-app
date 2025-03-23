package com.example.kueski.feature_flag.impl.extensions

fun Any.className(): String = this::class.java.name
fun Any.simpleClassName(): String = this::class.java.simpleName

inline fun <T> tryOrDefault(f: () -> T, defaultValue: T): T {
    return try {
        f()
    } catch (e: Exception) {
        defaultValue
    }
}

inline fun <T> tryOrDefaultWithOnError(f: () -> T, onError: (throwable: Throwable) -> T): T {
    return try {
        f()
    } catch (e: Exception) {
        onError(e)
    }
}