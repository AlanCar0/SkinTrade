package com.example.skintrade.Model


open class Productos(
    val id_p: Int,
    val nombre: String,
    val precio: Int,
    val imagen: String
) {
    override fun toString(): String = "$nombre, $${"%,d".format(precio)}"
}