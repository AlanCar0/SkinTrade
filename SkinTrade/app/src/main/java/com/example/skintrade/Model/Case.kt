package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for Cases.
 */
@Serializable
@SerialName("case")
data class Case(
    override val id: Int,
    override val name: String,
    override val price: Int,
    override val image: String,
    override val description: String,
    @SerialName("contains") val featuredContent: String
) : Product()
