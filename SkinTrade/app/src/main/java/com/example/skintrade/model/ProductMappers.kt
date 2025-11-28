package com.example.skintrade.model

import com.example.skintrade.data.local.ProductEntity
import com.example.skintrade.data.remote.ProductDto


// --- AGREGAR ESTA FUNCIÓN ---
fun ProductDto.toEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        image = image,
        description = description ?: "",
        productType = productType,
        category = category,
        condition = condition,
        featuredContent = featuredContent,
        author = author
    )
}

// --- AGREGAR ESTA OTRA (De Base de Datos a Modelo UI) ---
fun ProductEntity.toDomain(): Product {
    return when (productType) {
        "skin" -> Skin(id, name, price, image, description, condition ?: "", category ?: "")
        "agent" -> Agent(id, name, price, image, description, category ?: "")
        "case" -> Case(id, name, price, image, description, featuredContent ?: "")
        "soundtrack" -> Soundtrack(id, name, price, image, description, author ?: "")
        else -> object : Product() {
            override val id = this@toDomain.id
            override val name = this@toDomain.name
            override val price = this@toDomain.price
            override val image = this@toDomain.image
            override val description = this@toDomain.description
        }
    }
}