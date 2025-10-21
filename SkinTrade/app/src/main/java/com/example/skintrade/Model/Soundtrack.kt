package com.example.skintrade.Model

class Soundtrack(
    id_p: Int,
    nombre: String,
    precio: Int,
    val autor: String,
    imagen: String
) : Productos(id_p, nombre, precio, imagen) {

    init {
        require(nombre.isNotBlank()) { "El nombre del soundtrack no puede estar vacío" }
        require(autor.isNotBlank()) { "El autor no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, Autor: $autor, $${"%,d".format(precio)}"
}