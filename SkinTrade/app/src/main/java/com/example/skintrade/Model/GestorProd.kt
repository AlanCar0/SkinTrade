package com.example.skintrade.Model

object GestorProductos {
    private var contadorId = 1

    fun crearSkin(nombre: String, precio: Int, rareza: String,cat: String,estado: String): Skin {
        val skin = Skin(contadorId, nombre, precio, estado,cat)
        contadorId++
        return skin
    }

    fun crearAgente(nombre: String, precio: Int, rol: String,cat: String): Agente {
        val agente = Agente(contadorId, nombre, precio,cat)
        contadorId++
        return agente
    }

    fun crearSoundtrack(nombre: String, precio : Int, duracion: Int,autor: String): Soundtrack {
        val soundtrack = Soundtrack(contadorId, nombre, precio, autor)
        contadorId++
        return soundtrack
    }

    fun crearCaja(nombre: String, precio: Int, cont: String): Caja {
        val caja = Caja(contadorId, nombre, precio, cont)
        contadorId++
        return caja
    }
}