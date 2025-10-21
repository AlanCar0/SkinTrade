package com.example.skintrade.Model

object GestorProductos {
    private var contadorId = 1

    // Listas donde se guardan los productos
    private val skins = mutableListOf<Skin>()
    private val agentes = mutableListOf<Agente>()
    private val soundtracks = mutableListOf<Soundtrack>()
    private val cajas = mutableListOf<Caja>()

    // Crear una Skin y agregarla a la lista
    fun crearSkin(nombre: String, precio: Int, rareza: String, cat: String, estado: String): Skin {
        val skin = Skin(contadorId, nombre, precio, estado, cat)
        skins.add(skin)
        contadorId++
        return skin
    }

    // Crear un Agente
    fun crearAgente(nombre: String, precio: Int, rol: String, cat: String): Agente {
        val agente = Agente(contadorId, nombre, precio, cat)
        agentes.add(agente)
        contadorId++
        return agente
    }

    // Crear un Soundtrack
    fun crearSoundtrack(nombre: String, precio: Int, duracion: Int, autor: String): Soundtrack {
        val soundtrack = Soundtrack(contadorId, nombre, precio, autor)
        soundtracks.add(soundtrack)
        contadorId++
        return soundtrack
    }

    // Crear una Caja
    fun crearCaja(nombre: String, precio: Int, cont: String): Caja {
        val caja = Caja(contadorId, nombre, precio, cont)
        cajas.add(caja)
        contadorId++
        return caja
    }
/*
    fun eliminarProductoPorId(id_s: Int): Boolean {
        val eliminado = skins.removeIf { it.id == id } ||
                agentes.removeIf { it.id == id } ||
                soundtracks.removeIf { it.id == id } ||
                cajas.removeIf { it.id == id }
        return eliminado
    }

    // Mostrar todos los productos
    fun listarTodos(): List<Any> = skins + agentes + soundtracks + cajas*/
}