package com.example.skintrade.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@SerialName("caja")
class Caja(
    override val id_p: Int,
    override val nombre: String,
    override val precio: Int,
    override val imagen: String,
    val cont: String
) : Productos() { // <-- CORRECCIÓN: Hereda del constructor vacío

    init {
        require(nombre.isNotBlank()) { "El nombre de la caja no puede estar vacío" }
        require(cont.isNotBlank()) { "El contenido de la caja no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, $${"%,d".format(precio)}, Drop: $cont"
}