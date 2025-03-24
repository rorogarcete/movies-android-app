package com.example.kueski.network.impl.preferences

import android.content.Context
import android.content.SharedPreferences
import com.securepreferences.SecurePreferences
import androidx.core.content.edit

open class PreferencesBase {

    protected lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context, fileName: String) {
        sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    }

    fun initSecure(context: Context, fileName: String) {
        sharedPreferences = SecurePreferences(context, "", fileName)
    }

    operator fun set(key: String, value: String) {
        sharedPreferences.edit { putString(key, value) }
    }

    operator fun get(key: String): String? =
        sharedPreferences.getString(key, null)

    fun getOr(key: String, or: String): String =
        sharedPreferences.getString(key, or) ?: or

    operator fun set(key: String, value: Int) {
        sharedPreferences.edit { putInt(key, value) }
    }

    fun getInt(key: String): Int =
        sharedPreferences.getInt(key, 0)

    operator fun set(key: String, value: Float) {
        sharedPreferences.edit { putFloat(key, value) }
    }

    fun getFloat(key: String): Float = sharedPreferences.getFloat(key, 0f)

    operator fun set(key: String, value: Boolean) {
        sharedPreferences.edit { putBoolean(key, value) }
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    fun set(key: String, value: Long) {
        sharedPreferences.edit { putLong(key, value) }
    }

    fun getLong(key: String): Long = sharedPreferences.getLong(key, -1)

    operator fun contains(key: String): Boolean = sharedPreferences.contains(key)

    fun remove(key: String) {
        sharedPreferences.edit { remove(key) }
    }

    fun getAll(): Map<String, String> {
        return sharedPreferences.all.mapValues { it.value.toString() }
    }

    fun deleteAll() {
        sharedPreferences.edit { clear() }
    }
}