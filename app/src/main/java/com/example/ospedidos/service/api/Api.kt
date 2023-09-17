package com.example.ospedidos.service.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Api {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://ospedidos.com.br")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
}