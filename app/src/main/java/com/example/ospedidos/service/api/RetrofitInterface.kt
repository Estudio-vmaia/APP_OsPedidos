package com.example.ospedidos.service.api

import com.example.ospedidos.model.event.EventResponse
import com.example.ospedidos.model.login.Authenticator
import com.example.ospedidos.model.modules.ModuloResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RetrofitInterface {
    @GET("/API/login.php?")
    fun loginApi(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("usuario") phoneNumber: String?,
        @Query("senha") password: String?


    ): Call<Authenticator?>

    @GET("/API/loginSMS.php?")
    fun resetPasswordLogin(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("usuario") user: String?,
        @Query("senha") password: String?
    ): Call<Authenticator?>

    @GET("/API/modulos.php?")
    fun modules(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("slug") slug: String?,
        @Query("embed") embed: String?,
        @Query("usuario") user: String?
    ): Call<ModuloResponse?>?

    @GET("/API/eventos.php?")
    fun event(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("slug") slug: String?,
        @Query("embed") embed: String?,
        @Query("usuario") user: String?
    ): Call<EventResponse>?
}