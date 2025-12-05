package com.dk.mylibrary.utils

import android.content.Context
import com.dk.mylibrary.R
import java.util.Locale

object Common {

    fun getLang(mContext: Context): String? {
        val preferences =
            mContext.getSharedPreferences(mContext.packageName, Context.MODE_MULTI_PROCESS)
        return preferences.getString("KEY_LANG", "en")

    }

    fun setLang(context: Context, open: String?) {
        val preferences =
            context.getSharedPreferences(context.packageName, Context.MODE_MULTI_PROCESS)
        preferences.edit().putString("KEY_LANG", open).apply()
    }

    fun getLangName(mContext: Context): String? {
        val preferences =
            mContext.getSharedPreferences(mContext.packageName, Context.MODE_MULTI_PROCESS)
        return preferences.getString("KEY_LANG_NAME", "English")

    }

    fun setLangName(context: Context, open: String?) {
        val preferences =
            context.getSharedPreferences(context.packageName, Context.MODE_MULTI_PROCESS)
        preferences.edit().putString("KEY_LANG_NAME", open).apply()
    }

    fun setPosition(context: Context, open: Int) {
        val preferences =
            context.getSharedPreferences(context.packageName, Context.MODE_MULTI_PROCESS)
        preferences.edit().putInt("KEY_POSITION", open).apply()
    }

    fun getPosition(mContext: Context): Int {
        val preferences =
            mContext.getSharedPreferences(mContext.packageName, Context.MODE_MULTI_PROCESS)
        return preferences.getInt("KEY_POSITION", 0)
    }

    fun getPreLanguageflag(mContext: Context): Int {
        val preferences =
            mContext.getSharedPreferences(mContext.packageName, Context.MODE_MULTI_PROCESS)
        return preferences.getInt("KEY_FLAG", R.drawable.ic_english)
    }

    fun setPreLanguageflag(context: Context, flag: Int) {
        val preferences =
            context.getSharedPreferences(context.packageName, Context.MODE_MULTI_PROCESS)
        preferences.edit().putInt("KEY_FLAG", flag).apply()
    }

    fun setKeySolar(context: Context, open: String) {
        val preferences =
            context.getSharedPreferences(context.packageName, Context.MODE_MULTI_PROCESS)
        preferences.edit().putString("KEY_SOLAR", open).apply()
    }

    fun getKeySolar(mContext: Context): String {
        val preferences =
            mContext.getSharedPreferences(mContext.packageName, Context.MODE_MULTI_PROCESS)
        return preferences.getString("KEY_SOLAR","").toString()
    }

    fun setLocale(context: Context) {
        val language = Common.getLang(context) ?: "en"
        val myLocale = Locale(language)
        Locale.setDefault(myLocale)
        val resource = context.resources
        val displayMetrics = resource.displayMetrics
        val configuration = resource.configuration
        configuration.setLocale(myLocale)
        resource.updateConfiguration(configuration, displayMetrics)
    }

}