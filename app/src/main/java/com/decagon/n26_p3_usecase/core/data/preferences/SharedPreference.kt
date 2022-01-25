package com.decagon.n26_p3_usecase.core.data.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreference @Inject constructor(private val sharedPreferences: SharedPreferences) {

    /*Load details From Shared Preferences*/
    fun <T> loadFromSharedPref(prefType: String, key: String): T {
        return when (prefType) {
            "String" -> sharedPreferences.getString(key, "") as T
            "Int" -> sharedPreferences.getInt(key, 0) as T
            "Boolean" -> sharedPreferences.getBoolean(key, false) as T
            "Float" -> sharedPreferences.getFloat(key, 0f) as T
            "Long" -> sharedPreferences.getLong(key, 0) as T
            else -> sharedPreferences.getString(key, "") as T
        }
    }

    /*Save details to Shared Preferences*/
    fun <T> saveToSharedPref( prefKey : String, prefType : T) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        when (prefType) {
            is String -> editor.putString(prefKey, prefType)
            is Boolean -> editor.putBoolean(prefKey, prefType)
            is Int -> editor.putInt(prefKey, prefType)
            is Float -> editor.putFloat(prefKey, prefType)
            is Long -> editor.putLong(prefKey, prefType)
        }
        editor.apply()
    }

    /*Clear values in Shared Preferences*/
    fun clearSharedPref() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}
