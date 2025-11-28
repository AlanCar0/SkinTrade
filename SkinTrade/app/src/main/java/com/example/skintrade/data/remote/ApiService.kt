package com.example.skintrade.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ApiService {
    // Productos
    @GET("api/products")
    suspend fun getProducts(): List<ProductDto>

    @Multipart
    @POST("api/products")
    suspend fun createProduct(
        @Part("nombre") name: RequestBody,
        @Part("precio") price: RequestBody,
        @Part("description") description: RequestBody,
        @Part("productType") productType: RequestBody,
        @Part("category") category: RequestBody?,
        @Part("condition") condition: RequestBody?,
        @Part("author") author: RequestBody?,
        @Part("featuredContent") featuredContent: RequestBody?,
        @Part image: MultipartBody.Part?
    ): ProductDto

    @DELETE("api/products/{id}")
    suspend fun deleteProduct(@Path("id") id: Int)

    // Auth
    @FormUrlEncoded
    @POST("api/auth/register")
    suspend fun register(
        @Field("nombre") nombre: String,
        @Field("email") email: String,
        @Field("rut") rut: String,
        @Field("numero") numero: String,
        @Field("password") password: String
    ): Any // Devuelve un JSON genérico

    @FormUrlEncoded
    @POST("api/auth/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Map<String, Any> // Devuelve mapa con "role", "userId", etc.

    // Carrito
    @POST("api/orders/checkout")
    suspend fun checkout(@Body request: CheckoutRequestDto): Any
}

// Clase auxiliar para enviar el carrito
data class CheckoutRequestDto(
    val usuarioId: Long,
    val items: List<CartItemDto>
)
data class CartItemDto(val productoId: Int, val cantidad: Int)