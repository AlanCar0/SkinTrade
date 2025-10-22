package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("soundtrack")
class Soundtrack(
    override val id_p: Int,
    override val nombre: String,
    override val precio: Int,
    override val imagen: String,
    override val descripcion: String, // <-- PROPIEDAD AÑADIDA
    val autor: String
) : Productos() {

    init {
        require(nombre.isNotBlank()) { "El nombre del soundtrack no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, Autor: $autor, $${"%,d".format(precio)}"
}