package com.example.skintrade.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    // Si usas emulador: "http://10.0.2.2:8080/"
    // Si usas celular físico: Usa la IP de tu PC (ej: "http://192.168.1.50:8080/")
    private const val BASE_URL = "http://10.0.2.2:8082/"

    // URL para cargar imágenes
    const val IMAGE_URL = "${BASE_URL}api/products/images/"

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }
}