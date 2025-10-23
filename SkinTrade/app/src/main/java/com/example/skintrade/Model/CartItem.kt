package com.example.skintrade.Model

/**
 * Represents an item in the shopping cart.
 * Contains the product and its quantity.
 */
data class CartItem(
    val product: Product, // Corrected: Was 'producto: Productos'
    val quantity: Int
)
