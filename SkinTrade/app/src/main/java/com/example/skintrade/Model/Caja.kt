package com.example.skintrade.Model

class Caja(
    id_p: Int,
    nombre: String,
    precio: Int,
    val cont: String,
    imagen: String
) : Productos(id_p, nombre, precio, imagen) {

    init {
        require(nombre.isNotBlank()) { "El nombre de la caja no puede estar vacío" }
        require(cont.isNotBlank()) { "El contenido de la caja no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, $${"%,d".format(precio)}, Drop: $cont"
}