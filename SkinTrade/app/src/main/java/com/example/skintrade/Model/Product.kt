package com.example.skintrade.Model

import kotlinx.serialization.Serializable

@Serializable
abstract class Product {
    abstract val id: Int
    abstract val name: String
    abstract val price: Int
    abstract val image: String
    abstract val description: String
}
