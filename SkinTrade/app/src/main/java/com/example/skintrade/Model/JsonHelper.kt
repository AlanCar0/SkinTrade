package com.example.skintrade.Model

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.decodeFromString

/**
 * Módulo que le enseña a kotlinx.serialization cómo manejar la herencia de la clase Product.
 * Asocia el valor del campo "type" del JSON con la clase de Kotlin correspondiente.
 */
private val module = SerializersModule { 
    polymorphic(Product::class) { 
        subclass(Skin::class, Skin.serializer()) 
        subclass(Agent::class, Agent.serializer())
        subclass(Case::class, Case.serializer())
        subclass(Soundtrack::class, Soundtrack.serializer())
    } 
}

/**
 * Objeto Json configurado para usar nuestro módulo de polimorfismo.
 */
private val jsonParser = Json { 
    serializersModule = module
    classDiscriminator = "type"
    ignoreUnknownKeys = true
}

/**
 * Función que lee un archivo JSON desde la carpeta 'assets' y lo convierte
 * en una lista de objetos, usando nuestro parser configurado.
 */
fun loadProductsFromAssets(context: Context, fileName: String): List<Product>? {
    return try {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        jsonParser.decodeFromString<List<Product>>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
