package com.example.skintrade.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.skintrade.model.*
import com.example.skintrade.Repository.ProductRepository
import com.example.skintrade.data.remote.CartItemDto
import com.example.skintrade.data.remote.CheckoutRequestDto
import com.example.skintrade.data.remote.RetrofitClient
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class SharedViewModel(application: Application) : AndroidViewModel(application) {

    // Instancia del Repositorio (Maneja Room + Retrofit para productos)
    private val repository = ProductRepository(application)

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val productsFlow: StateFlow<List<Product>> = _allProducts.asStateFlow()
    val products: List<Product> get() = _allProducts.value

    private val _uiEvents = MutableStateFlow<String?>(null)
    val uiEvents: StateFlow<String?> = _uiEvents.asStateFlow()

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems.asStateFlow()

    // Datos de sesión
    var currentUserId: Long? = null
    var currentUserRole: String? = null

    val totalPrice: StateFlow<Double> = _cartItems
        .map { list -> list.sumOf { it.product.price * it.quantity } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    init {
        loadProducts()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            try {
                // Usamos el Repositorio (Carga de BD local si no hay internet)
                val lista = repository.getProducts()
                _allProducts.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
                _uiEvents.value = "Error cargando datos: ${e.message}"
            }
        }
    }

    // ---------------- AUTH (LOGIN/REGISTER) ----------------
    fun login(email: String, pass: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.login(email, pass)
                val role = response["role"] as? String
                // Gson a veces parsea números como Double
                val userIdDouble = response["userId"] as? Double
                currentUserId = userIdDouble?.toLong()
                currentUserRole = role

                if (role != null) {
                    onResult(role) // "ADMIN" o "USER"
                } else {
                    _uiEvents.value = "Login fallido: Respuesta inesperada"
                    onResult(null)
                }
            } catch (e: Exception) {
                _uiEvents.value = "Error Login: ${e.message}"
                onResult(null)
            }
        }
    }

    fun register(nombre: String, email: String, rut: String, numero: String, pass: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.register(nombre, email, rut, numero, pass)
                _uiEvents.value = "Registro exitoso"
                onSuccess()
            } catch (e: Exception) {
                _uiEvents.value = "Error Registro: ${e.message}"
            }
        }
    }

    // ---------------- CARRITO ----------------
    fun addToCart(product: Product) {
        _cartItems.update { current ->
            val existing = current.find { it.product.id == product.id }
            if (existing != null) {
                current.map { if (it.product.id == product.id) it.copy(quantity = it.quantity + 1) else it }
            } else {
                current + CartItem(product, 1)
            }
        }
        _uiEvents.value = "Producto agregado"
    }

    fun incrementItem(item: CartItem) {
        _cartItems.update { currentList ->
            currentList.map {
                if (it.product.id == item.product.id) it.copy(quantity = it.quantity + 1) else it
            }
        }
    }

    fun decrementItem(item: CartItem) {
        _cartItems.update { currentList ->
            if (item.quantity > 1) {
                currentList.map {
                    if (it.product.id == item.product.id) it.copy(quantity = it.quantity - 1) else it
                }
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
        val uid = currentUserId
        if (uid == null) {
            _uiEvents.value = "Debes iniciar sesión para comprar"
            return
        }
        if (_cartItems.value.isEmpty()) {
            _uiEvents.value = "El carrito está vacío"
            return
        }

        viewModelScope.launch {
            try {
                val itemsDto = _cartItems.value.map { CartItemDto(it.product.id, it.quantity) }
                val request = CheckoutRequestDto(uid, itemsDto)

                RetrofitClient.apiService.checkout(request)

                _cartItems.value = emptyList()
                _uiEvents.value = "¡Compra exitosa!"
            } catch (e: Exception) {
                _uiEvents.value = "Error al comprar: ${e.message}"
            }
        }
    }

    // ---------------- ADMIN (CRUD PRODUCTOS) ----------------
    fun addProduct(product: Product) {
        viewModelScope.launch {
            try {
                // 1. Preparar campos comunes
                val nameRB = createPart(product.name)
                val priceRB = createPart(product.price.toString())
                val descRB = createPart(product.description)

                // 2. Preparar campos específicos según el tipo
                var type = ""
                var categoryRB: RequestBody? = null
                var conditionRB: RequestBody? = null
                var authorRB: RequestBody? = null
                var featuredRB: RequestBody? = null

                when (product) {
                    is Skin -> {
                        type = "skin"
                        categoryRB = createPart(product.category)
                        conditionRB = createPart(product.condition)
                    }
                    is Agent -> {
                        type = "agent"
                        categoryRB = createPart(product.category)
                    }
                    is Case -> {
                        type = "case"
                        featuredRB = createPart(product.featuredContent)
                    }
                    is Soundtrack -> {
                        type = "soundtrack"
                        authorRB = createPart(product.author)
                    }
                }
                val typeRB = createPart(type)

                // 3. Llamar a la API (Sin imagen real por ahora, mandamos null)
                // Nota: Tu ApiService espera 'image' como MultipartBody.Part?
                RetrofitClient.apiService.createProduct(
                    name = nameRB,
                    price = priceRB,
                    description = descRB,
                    productType = typeRB,
                    category = categoryRB,
                    condition = conditionRB,
                    author = authorRB,
                    featuredContent = featuredRB,
                    image = null // Aquí iría el archivo si tuvieras un ImagePicker
                )

                // 4. Recargar lista (Esto actualiza Room y la UI)
                loadProducts()
                _uiEvents.value = "Producto agregado correctamente"

            } catch (e: Exception) {
                e.printStackTrace()
                _uiEvents.value = "Error al crear: ${e.message}"
            }
        }
    }

    fun removeProduct(product: Product) {
        viewModelScope.launch {
            try {
                RetrofitClient.apiService.deleteProduct(product.id)
                loadProducts() // Recargar para actualizar la lista local
                _uiEvents.value = "Producto eliminado"
            } catch(e: Exception) {
                _uiEvents.value = "Error al eliminar: ${e.message}"
            }
        }
    }

    fun clearUiEvent() {
        _uiEvents.value = null
    }

    // Helper para crear RequestBody texto
    private fun createPart(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }
}