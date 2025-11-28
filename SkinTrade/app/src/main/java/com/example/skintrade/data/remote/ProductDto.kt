package com.example.skintrade.data.remote

import com.google.gson.annotations.SerializedName

// Esta clase coincide EXACTAMENTE con el JSON que envía tu Spring Boot
data class ProductDto(
    val id: Int,
    @SerializedName("nombre") val name: String,
    @SerializedName("precio") val price: Double,
    @SerializedName("imagen") val image: String, // ej: "ak47.jpg"
    val description: String?,
    @SerializedName("productType") val productType: String, // "skin", "agent", etc.

    // Campos opcionales
    val category: String?,
    val condition: String?,
    @SerializedName("featuredContent") val featuredContent: String?,
    val author: String?
)