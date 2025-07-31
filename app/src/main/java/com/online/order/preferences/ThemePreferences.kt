package com.online.order.preferences

import android.content.Context
import com.google.gson.Gson
import com.online.order.data.ThemeType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

 open class ThemePreferences @Inject constructor(@ApplicationContext private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)
     private val gson = Gson()

    companion object{
        private const val  KEY_THEME_ID = "SELECTED_THEME_ID"
    }


    fun saveThemeId(themeId : ThemeType){
        val json = gson.toJson(themeId)
        sharedPreferences.edit().putString(KEY_THEME_ID, json).apply()
    }

    fun getSavedThemeId() : ThemeType?{
        val json = sharedPreferences.getString(KEY_THEME_ID, null)
        return try {
            gson.fromJson(json, ThemeType::class.java)
        }catch (e : Exception){
            null

        }
    }
}