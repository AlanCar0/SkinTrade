package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("skin")
class Skin(
    override val id_p: Int,
    override val nombre: String,
    override val precio: Int,
    override val imagen: String,
    val estado: String,
    val cat: String
) : Productos() { // <-- CORRECCIÓN: Hereda del constructor vacío

    init {
        require(nombre.isNotBlank()) { "El nombre de la skin no puede estar vacío" }
        require(cat.isNotBlank()) { "La categoría no puede estar vacía" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, Categoria: $cat, $${"%,d".format(precio)}, Estado: $estado"
}