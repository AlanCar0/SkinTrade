package com.example.skintrade.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("case")
data class Case(
    override val id: Int,
    override val name: String,
    override val price: Double,
    override val image: String,
    override val description: String,
    @SerialName("contains") val featuredContent: String
) : Product()
