package com.example.skintrade.Model

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass // AÑADO ESTE IMPORT
import kotlinx.serialization.decodeFromString

/**
 * Módulo que le enseña a kotlinx.serialization cómo manejar la herencia de la clase Productos.
 * Asocia el valor del campo "type" del JSON con la clase de Kotlin correspondiente.
 */
private val module = SerializersModule { 
    polymorphic(Productos::class) { 
        subclass(Skin::class)
        subclass(Agente::class)
        subclass(Caja::class)
        subclass(Soundtrack::class)
    } 
}

/**
 * Objeto Json configurado para usar nuestro módulo de polimorfismo.
 * - `serializersModule = module`: Conecta nuestro "manual" de clases al lector de JSON.
 * - `classDiscriminator = "type"`: Le dice que el campo "type" en el JSON define la clase.
 * - `ignoreUnknownKeys = true`: Evita errores si el JSON tiene campos que no existen en tu clase.
 */
val jsonParser = Json { 
    serializersModule = module
    classDiscriminator = "type"
    ignoreUnknownKeys = true
}

/**
 * Función que lee un archivo JSON desde la carpeta 'assets' y lo convierte
 * en una lista de objetos, usando nuestro parser configurado.
 */
fun loadProductosFromAssets(context: Context, fileName: String): List<Productos>? {
    return try {
        val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        jsonParser.decodeFromString<List<Productos>>(jsonString)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}