package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for Soundtracks.
 */
@Serializable
@SerialName("soundtrack")
data class Soundtrack(
    override val id: Int,
    override val name: String,
    override val price: Int,
    override val image: String,
    override val description: String,
    val author: String
) : Product()
