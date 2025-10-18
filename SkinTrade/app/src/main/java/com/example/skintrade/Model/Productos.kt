package com.example.skintrade.Model

open class Productos(val id_p: Int, var nombre: String, var precio: Int) {

    init {
        require(nombre.isNotBlank()) { "El nombre no puede estar vacío" }
        require(precio >= 0) { "El precio no puede ser negativo" }
    }

    override fun toString(): String {
        return "Producto($id_p $nombre $precio)"
    }
}