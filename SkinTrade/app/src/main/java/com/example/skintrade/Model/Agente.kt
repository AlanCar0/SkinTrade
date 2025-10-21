package com.example.skintrade.Model
import kotlinx.serialization.Serializable

@Serializable
class Agente(
    id_p: Int,
    nombre: String,
    precio: Int,
    val cat: String,
    imagen: String
) : Productos(id_p, nombre, precio, imagen) {

    init {
        require(nombre.isNotBlank()) { "El nombre del agente no puede estar vacío" }
        require(cat.isNotBlank()) { "La categoría del agente no puede estar vacía" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, Categoria: $cat, $${"%,d".format(precio)}"
}