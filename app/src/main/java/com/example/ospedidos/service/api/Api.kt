package com.example.ospedidos.service.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Api {
    private val BASE_URL = "https://ospedidos.com.br"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Define o nível de log que você deseja
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient) // Configure o cliente OkHttpClient com o interceptor de log
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service: RetrofitInterface = retrofit.create(RetrofitInterface::class.java)
}
