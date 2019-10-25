package com.rittamann.minichecklist.utils

import android.content.Context

object SharedPreferencesUtil {
    private const val MyPREFERENCES = "com.rittmann.minichecklist"
    private const val HOST = "HOST_PREFS"

    private fun getEditor(context: Context) =
        context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE)

    fun setHost(context: Context, value: String) {
        getEditor(context).edit().putString(HOST, value).apply()
    }

    fun getHost(context: Context): String {
        return getEditor(context).getString(HOST, "")
    }
}