package com.example.skintrade.Model

class Soundtrack (
    id_p: Int,
    nombre: String,
    precio: Int,
    val autor: String


): Productos(id_p,nombre,precio){
    override fun toString(): String {
        return "$nombre , Autor: $autor , $${"%,d".format(precio)}"
    }
}