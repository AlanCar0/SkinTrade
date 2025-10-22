package com.example.skintrade.Model

import kotlinx.serialization.Serializable

/**
 * Clase base abstracta para todos los productos.
 * Declara las propiedades como 'abstract' para que las clases hijas las implementen.
 * Esto evita el error de nombres duplicados en la serialización.
 */
@Serializable
abstract class Productos {
    abstract val id_p: Int
    abstract val nombre: String
    abstract val precio: Int
    abstract val imagen: String

    override fun toString(): String = "$nombre, $${"%,d".format(precio)}"
}