package com.example.skintrade.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("skin")
data class Skin(
    override val id: Int,
    override val name: String,
    override val price: Double,
    override val image: String,
    override val description: String,
    val condition: String,
    @SerialName("category") val category: String
) : Product()
