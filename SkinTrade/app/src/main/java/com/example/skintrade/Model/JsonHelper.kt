package com.example.skintrade.Model

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.decodeFromString

private val module = SerializersModule { 
    polymorphic(Product::class) { 
        subclass(Skin::class, Skin.serializer()) 
        subclass(Agent::class, Agent.serializer())
        subclass(Case::class, Case.serializer())
        subclass(Soundtrack::class, Soundtrack.serializer())
    } 
}


private val jsonParser = Json { 
    serializersModule = module
    classDiscriminator = "type"
    ignoreUnknownKeys = true
}


fun loadProductsFromAssets(context: Context, fileName: String): List<Product>? {
    return try {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        jsonParser.decodeFromString<List<Product>>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
