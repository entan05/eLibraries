package jp.team.eworks.e_core_library.data

import android.content.Context

@Suppress("unused", "SameParameterValue")
abstract class BaseSharedPreferences {
    protected interface PrefName {
        val nameKey: String
    }

    protected interface Pref<T: PrefName> {
        val prefName: T
        val key: String
    }

    protected open lateinit var context: Context

    open fun initialize(context: Context) {
        this.context = context
    }

    protected fun getBoolean(pref: Pref<*>, defValue: Boolean): Boolean =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getBoolean(pref.key, defValue)

    protected fun setBoolean(pref: Pref<*>, value: Boolean) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putBoolean(pref.key, value).apply()

    protected fun getString(pref: Pref<*>, defValue: String): String =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getString(pref.key, defValue)!!

    protected fun getStringNullable(pref: Pref<*>, defValue: String?): String? =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getString(pref.key, defValue)

    protected fun setString(pref: Pref<*>, value: String) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putString(pref.key, value).apply()

    protected fun setStringNullable(pref: Pref<*>, value: String?) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putString(pref.key, value).apply()

    protected fun getInt(pref: Pref<*>, defValue: Int): Int =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getInt(pref.key, defValue)

    protected fun setInt(pref: Pref<*>, value: Int) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putInt(pref.key, value).apply()

    protected fun getFloat(pref: Pref<*>, defValue: Float): Float =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getFloat(pref.key, defValue)

    protected fun setFloat(pref: Pref<*>, value: Float) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putFloat(pref.key, value).apply()

    protected fun getLong(pref: Pref<*>, defValue: Long): Long =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .getLong(pref.key, defValue)

    protected fun setLong(pref: Pref<*>, value: Long) =
        context.getSharedPreferences(pref.prefName.nameKey, Context.MODE_PRIVATE)
            .edit().putLong(pref.key, value).apply()
}
