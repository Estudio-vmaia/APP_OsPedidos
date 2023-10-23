package com.example.ospedidos.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ospedidos.model.category.Category
import com.example.ospedidos.model.event.Event
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_SLUG = "slug"
    private const val KEY_EMBED = "embed"
    private const val KEY_USER = "user"
    private const val KEY_EVENT_LIST = "eventList"
    private const val KEY_ID_EVENT= "idEvent"
    private const val KEY_CATEGORY_LIST = "categoryList"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLoginData(context: Context, slug: String?, embed: String?, user: String?) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_SLUG, slug)
        editor.putString(KEY_EMBED, embed)
        editor.putString(KEY_USER, user)
        editor.apply()
    }

    fun getSlug(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_SLUG, null)
    }

    fun getEmbed(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_EMBED, null)
    }

    fun getUser(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_USER, null)
    }

    fun saveIdEvent(context: Context, idEvent: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_ID_EVENT, idEvent)
        editor.apply()
    }

    fun getIdEvent(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ID_EVENT, null)
    }
    fun saveEventList(context: Context, eventList: List<Event>) {
        val json = Gson().toJson(eventList)
        val prefs = context.getSharedPreferences(KEY_EVENT_LIST, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("eventList", json)
        editor.apply()
    }

    fun getEventList(context: Context): List<Event> {
        val prefs = context.getSharedPreferences(KEY_EVENT_LIST, Context.MODE_PRIVATE)
        val json = prefs.getString("eventList", null)
        return if (json != null) {
            val type = object : TypeToken<List<Event>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveCategoryList(context: Context, categoryList: List<Category>) {
        val json = Gson().toJson(categoryList)
        val prefs = context.getSharedPreferences(KEY_CATEGORY_LIST, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("categoryList", json)
        editor.apply()
    }

    fun getCategoryList(context: Context): List<Category> {
        val prefs = context.getSharedPreferences(KEY_CATEGORY_LIST, Context.MODE_PRIVATE)
        val json = prefs.getString("categoryList", null)
        return if (json != null) {
            val type = object : TypeToken<List<Category>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
