package com.example.skintrade.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val description: String,
    val productType: String, // "skin", "agent", etc.

    // Campos opcionales (Nullable)
    val category: String?,
    val condition: String?,
    val featuredContent: String?,
    val author: String?
)