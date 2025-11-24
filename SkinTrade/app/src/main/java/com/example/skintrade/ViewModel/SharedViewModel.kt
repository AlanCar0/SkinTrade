package com.example.skintrade.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skintrade.Model.CartItem
import com.example.skintrade.Model.Product
import com.example.skintrade.Model.loadProductsFromAssets
import kotlinx.coroutines.flow.*

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val products: List<Product> get() = _allProducts.value

    private val _uiEvents = MutableStateFlow<String?>(null)
    val uiEvents: StateFlow<String?> = _uiEvents.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    val totalPrice: StateFlow<Double> = _cartItems
        .map { list -> list.sumOf { it.product.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        _allProducts.value = loadProducts(application)
    }

    private fun loadProducts(application: Application): List<Product> {
        val skins = loadProductsFromAssets(application, "skins.json") ?: emptyList()
        val agents = loadProductsFromAssets(application, "agents.json") ?: emptyList()
        val cases = loadProductsFromAssets(application, "cases.json") ?: emptyList()
        val soundtracks = loadProductsFromAssets(application, "soundtracks.json") ?: emptyList()
        return skins + agents + cases + soundtracks
    }

    // ---------------- CARRITO ----------------
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
        _cartItems.update { currentList -> currentList.filterNot { it.product.id == item.product.id } }
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

    // ---------------- ADMIN CRUD ----------------
    fun addProduct(product: Product) {
        _allProducts.value = _allProducts.value + product
    }

    fun removeProduct(product: Product) {
        _allProducts.value = _allProducts.value - product
    }
}