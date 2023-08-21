package com.example.ospedidos.service.api

import com.example.ospedidos.model.Autenticacao
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface RetrofitInterface {
        @GET("/API/login.php?usuario={usuario}&senha={senha}")
        fun osPedidosLogin(
            @Query("usuario") user: String?,
            @Query("senha") password: String?,
        ): Call<List<Autenticacao?>?>?
}