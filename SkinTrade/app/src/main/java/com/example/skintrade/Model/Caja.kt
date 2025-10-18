package com.example.skintrade.Model

class Caja (
    id_p: Int,
    nombre: String,
    precio: Int,
    val cont: String


): Productos(id_p,nombre,precio){
    override fun toString(): String {
        return "$nombre , $${"%,d".format(precio)}, Drop: $cont"
    }
}