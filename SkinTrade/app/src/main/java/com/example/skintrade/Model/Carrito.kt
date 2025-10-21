package com.example.skintrade.Model

class Carrito {


    private val productos = mutableListOf<Productos>()

    fun agregarProducto(producto: Productos) {
        productos.add(producto)
        println("${producto.nombre} fue agregado al carrito.")
    }


    fun eliminarProducto(id: Int): Boolean {
        val producto = productos.find { it.id_p == id }
        return if (producto != null) {
            productos.remove(producto)
            println("${producto.nombre} fue eliminado del carrito.")
            true
        } else {
            println("No se encontró el producto con ID $id.")
            false
        }
    }


    fun vaciarCarrito() {
        productos.clear()
        println("El carrito ha sido vaciado.")
    }


    fun total(): Int {
        return productos.sumOf { it.precio }
    }


    fun mostrarCarrito(): String {
        return if (productos.isEmpty()) {
            "El carrito está vacío."
        } else {
            buildString {
                appendLine("🛒 Carrito de compras:")
                productos.forEach { p ->
                    appendLine("- ${p.toString()}")
                }
                appendLine("Total: $${"%,d".format(total())}")
            }
        }
    }

    // Obtener lista inmutable, para la vista
    fun obtenerProductos(): List<Productos> = productos.toList()
}