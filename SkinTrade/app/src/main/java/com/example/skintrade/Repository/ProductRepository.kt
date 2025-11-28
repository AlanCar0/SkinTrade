package com.example.skintrade.Repository

import android.content.Context
import com.example.skintrade.data.local.AppDatabase
import com.example.skintrade.model.*
import com.example.skintrade.model.toDomain
import com.example.skintrade.model.toEntity
import com.example.skintrade.data.remote.RetrofitClient

class ProductRepository(context: Context) {

    private val dao = AppDatabase.getInstance(context).productoDao()

    suspend fun getProducts(): List<Product> {
        // 1. Intentar actualizar desde Internet
        try {
            val remoteDtos = RetrofitClient.apiService.getProducts()
            if (remoteDtos.isNotEmpty()) {
                // Limpiar cache vieja y guardar la nueva
                dao.clearAll()
                dao.insertAll(remoteDtos.map { it.toEntity() })
            }
        } catch (e: Exception) {
            e.printStackTrace()
            // Si falla internet, no pasa nada, usamos lo que haya en la BD
        }

        // 2. La "Fuente de la Verdad" es la Base de Datos Local
        val localEntities = dao.getAll()
        return localEntities.map { it.toDomain() }
    }
}