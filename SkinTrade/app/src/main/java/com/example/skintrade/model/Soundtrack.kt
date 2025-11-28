package com.example.skintrade.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("soundtrack")
data class Soundtrack(
    override val id: Int,
    override val name: String,
    override val price: Double,
    override val image: String,
    override val description: String,
    val author: String
) : Product()
