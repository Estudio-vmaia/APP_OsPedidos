package com.example.ospedidos.service.api

import com.example.ospedidos.model.Authenticator
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RetrofitInterface {
    @GET("/API/login.php?")
    fun loginApi(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("senha") password: String?,
        @Query("usuario") phoneNumber: String?


    ): Call<Authenticator?>
    @GET("/API/loginSMS.php?")
    fun resetPasswordLogin(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("usuario") user: String?,
        @Query("senha") password: String?


    ): Call<Authenticator?>
}