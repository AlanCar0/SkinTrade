package com.example.skintrade.model

import kotlinx.serialization.Serializable

@Serializable
abstract class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Double
    abstract val image: String
    abstract val description: String
}
