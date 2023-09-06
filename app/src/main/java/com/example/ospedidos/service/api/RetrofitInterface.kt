package com.example.ospedidos.service.api

import com.example.ospedidos.model.Autenticacao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface RetrofitInterface {
    @GET("/API/login.php?")
    fun osPedidosLogin(
        @Header("Content-Type") contentType: String,
        @Header("Authorization") authorization: String,
        @Query("usuario") user: String?,
        @Query("senha") password: String?

    ): Call<Autenticacao?>
}