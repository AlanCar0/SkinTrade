package com.example.skintrade.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skintrade.Model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    val products: List<Product>

    private val _uiEvents = MutableStateFlow<String?>(null)
    val uiEvents: StateFlow<String?> = _uiEvents.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalPrice: StateFlow<Double> = _cartItems
        .map { list -> list.sumOf { it.product.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        products = loadProducts(application)
    }

    private fun loadProducts(application: Application): List<Product> {
        val skins = loadProductsFromAssets(application, "skins.json") ?: emptyList()
        val agents = loadProductsFromAssets(application, "agents.json") ?: emptyList()
        val cases = loadProductsFromAssets(application, "cases.json") ?: emptyList()
        val soundtracks = loadProductsFromAssets(application, "soundtracks.json") ?: emptyList()
        return skins + agents + cases + soundtracks
    }

    fun addToCart(product: Product) {
        _cartItems.update { currentList ->
            val existingItem = currentList.find { it.product.id == product.id }
            if (existingItem != null) {
                currentList.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
            } else {
                currentList + CartItem(product = product, quantity = 1)
            }
        }
    }

    fun incrementItem(item: CartItem) {
        _cartItems.update { currentList ->
            currentList.map { if (it.product.id == item.product.id) it.copy(quantity = it.quantity + 1) else it }
        }
    }

    fun decrementItem(item: CartItem) {
        _cartItems.update { currentList ->
            if (item.quantity > 1) {
                currentList.map { if (it.product.id == item.product.id) it.copy(quantity = it.quantity - 1) else it }
            } else {
                currentList.filterNot { it.product.id == item.product.id }
            }
        }
    }

    fun removeFromCart(item: CartItem) {
        _cartItems.update { currentList ->
            currentList.filterNot { it.product.id == item.product.id }
        }
    }
    
    fun checkout() {
        val itemWithNoStock = _cartItems.value.find { it.quantity > 4 }

        if (itemWithNoStock != null) {
            _uiEvents.value = "¡Compra rechazada! La cantidad para '${itemWithNoStock.product.name}' supera el stock (4)."
        } else {
            _uiEvents.value = "¡Compra exitosa!"
            _cartItems.value = emptyList()
        }
    }

    fun clearUiEvent() {
        _uiEvents.value = null
    }
}