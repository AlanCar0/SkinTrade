package com.example.skintrade.Model

class Agente (
    id_p: Int,
    nombre: String,
    precio: Int,
    val cat: String


): Productos(id_p,nombre,precio){
    override fun toString(): String {
        return "$nombre , Categoria: $cat , $${"%,d".format(precio)}"
    }
}