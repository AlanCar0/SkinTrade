package com.example.skintrade.Model

class Skin(
    id_p: Int,
    nombre: String,
    precio: Int,
    val estado: String,
    val cat: String,
    imagen: String
) : Productos(id_p, nombre, precio, imagen) {

    init {
        require(nombre.isNotBlank()) { "El nombre de la skin no puede estar vacío" }
        require(cat.isNotBlank()) { "La categoría no puede estar vacía" }
        require(estado.isNotBlank()) { "El estado no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
        require(imagen.isNotBlank()) { "La imagen no puede estar vacía" }
    }

    override fun toString() = "$nombre, Categoria: $cat, $${"%,d".format(precio)}, Estado: $estado"
}