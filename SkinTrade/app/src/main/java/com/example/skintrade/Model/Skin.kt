package com.example.skintrade.Model

class Skin (
    id_p: Int,
    nombre: String,
    precio: Int,
    val estado: String,
    val cat: String


): Productos(id_p,nombre,precio){
    override fun toString(): String {
        return "$nombre , Categoria: $cat , $${"%,d".format(precio)} , Estado: $estado"
    }
}