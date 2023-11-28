package com.example.ospedidos.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.ospedidos.presentation.model.category.Category
import com.example.ospedidos.presentation.model.event.Event
import com.example.ospedidos.presentation.model.modules.Modulo
import com.example.ospedidos.presentation.model.product.Product
import com.example.ospedidos.presentation.presenter.ProductWithQuantity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object SharedPreferenceManager {
    private const val PREF_NAME = "MyAppPrefs"
    private const val KEY_SLUG = "slug"
    private const val KEY_EMBED = "embed"
    private const val KEY_USER = "user"
    private const val KEY_EVENT_LIST = "eventList"
    private const val KEY_EVENT_RESPONSE_API = "eventList"
    private const val KEY_MODULE_LIST = "moduleList"
    private const val KEY_ID_EVENT = "idEvent"
    private const val KEY_ID_CATEGORY = "idCategory"
    private const val KEY_ID_PRODUCT = "idProduct"
    private const val KEY_CATEGORY_LIST = "categoryList"
    private const val KEY_PRODUCT_LIST = "categoryList"
    private const val SELECTED_EVENT_ID_KEY = "SelectedEventId"
    private const val KEY_PRODUCT_DATA = "productData"
    private const val KEY_CART = "productCart"
    private const val KEY_TOTAL_VALUE = "totalValue"

    fun saveTotalValue(context: Context, totalValue: Double) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putFloat(KEY_TOTAL_VALUE, totalValue.toFloat()) // Salvar como float
        editor.apply()
    }

    fun getTotalValue(context: Context): Double {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return prefs.getFloat(KEY_TOTAL_VALUE, 0.0f).toDouble() // Retornar como double
    }
    fun saveCart(context: Context, cart: List<ProductWithQuantity>) {
        val json = Gson().toJson(cart)
        val prefs = context.getSharedPreferences(KEY_CART, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("cart", json)
        editor.apply()
    }

    fun getCart(context: Context): List<ProductWithQuantity> {
        val prefs = context.getSharedPreferences(KEY_CART, Context.MODE_PRIVATE)
        val json = prefs.getString("cart", null)
        return if (json != null) {
            val type = object : TypeToken<List<ProductWithQuantity>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveProductData(context: Context, productData: String) {
        val prefs = context.getSharedPreferences(KEY_PRODUCT_DATA, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("productData", productData)
        editor.apply()
    }

    fun getProductData(context: Context): String? {
        val prefs = context.getSharedPreferences(KEY_PRODUCT_DATA, Context.MODE_PRIVATE)
        return prefs.getString("productData", null)
    }


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

    fun saveIdCategory(context: Context, idCategory: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_ID_CATEGORY, idCategory)
        editor.apply()
    }

    fun saveIdProduct(context: Context, idProduct: String) {
        val editor = getSharedPreferences(context).edit()
        editor.putString(KEY_ID_PRODUCT, idProduct)
        editor.apply()
    }

    fun getIdEvent(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ID_EVENT, null)
    }

    fun getIdCategory(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ID_CATEGORY, null)
    }

    fun getIdProduct(context: Context): String? {
        return getSharedPreferences(context).getString(KEY_ID_PRODUCT, null)
    }

    fun saveModuleList(context: Context, moduleList: List<Modulo>) {
        val json = Gson().toJson(moduleList)
        val prefs = context.getSharedPreferences(KEY_MODULE_LIST, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("moduleList", json)
        editor.apply()
    }

    fun getModuleList(context: Context): List<Modulo> {
        val prefs = context.getSharedPreferences(KEY_MODULE_LIST, Context.MODE_PRIVATE)
        val json = prefs.getString("moduleList", null)
        return if (json != null) {
            val type = object : TypeToken<List<Modulo>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun saveSelectedEventId(context: Context, eventId: String, nome: String) {
        val prefs = context.getSharedPreferences(SELECTED_EVENT_ID_KEY, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("eventId", eventId)
        editor.putString("nome", nome)
        editor.apply()
    }

    fun getSelectedEventId(context: Context): Pair<String, String>? {
        val prefs = context.getSharedPreferences(SELECTED_EVENT_ID_KEY, Context.MODE_PRIVATE)
        val eventId = prefs.getString("eventId", null)
        val nome = prefs.getString("nome", null)
        return if (eventId != null && nome != null) {
            Pair(eventId, nome)
        } else {
            null
        }
    }


    fun saveEventResponseApi(
        context: Context,
        arrayName: String,
        arrayColunas: String,
        arrayKeys: String
    ) {
        val prefs = context.getSharedPreferences(KEY_EVENT_RESPONSE_API, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("arrayName", arrayName)
        editor.putString("arrayColunas", arrayColunas)
        editor.putString("arrayKeys", arrayKeys)
        editor.apply()
    }
    fun getEventResponseApi(context: Context): Triple<String?, String?, String?> {
        val prefs = context.getSharedPreferences(KEY_EVENT_RESPONSE_API, Context.MODE_PRIVATE)

        val arrayName = prefs.getString("arrayName", null)
        val arrayColunas = prefs.getString("arrayColunas", null)
        val arrayKeys = prefs.getString("arrayKeys", null)

        return Triple(arrayName, arrayColunas, arrayKeys)
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

    fun saveProductList(context: Context, productList: List<Product>) {
        val json = Gson().toJson(productList)
        val prefs = context.getSharedPreferences(KEY_PRODUCT_LIST, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString("productList", json)
        editor.apply()
    }

    fun getProductList(context: Context): List<Product> {
        val prefs = context.getSharedPreferences(KEY_PRODUCT_LIST, Context.MODE_PRIVATE)
        val json = prefs.getString("productList", null)
        return if (json != null) {
            val type = object : TypeToken<List<Product>>() {}.type
            Gson().fromJson(json, type)
        } else {
            emptyList()
        }
    }
}
