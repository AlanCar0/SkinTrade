package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data class for Agents.
 */
@Serializable
@SerialName("agent")
data class Agent(
    override val id: Int,
    override val name: String,
    override val price: Int,
    override val image: String,
    override val description: String,
    @SerialName("category") val category: String
) : Product()
