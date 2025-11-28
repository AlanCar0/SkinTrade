package com.example.skintrade

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skintrade.view.*
import com.example.skintrade.viewmodel.SharedViewModel
import com.example.skintrade.View.*

class MainActivity : ComponentActivity() {

    private val viewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            // Estados del ViewModel
            val products by viewModel.productsFlow.collectAsState() // Usamos el Flow actualizado
            val cartItems by viewModel.cartItems.collectAsState()
            val totalPrice by viewModel.totalPrice.collectAsState()
            val uiEvent by viewModel.uiEvents.collectAsState()

            // Manejo de Mensajes (Toasts) que vienen del ViewModel
            LaunchedEffect(uiEvent) {
                uiEvent?.let { message ->
                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                    viewModel.clearUiEvent()

                    // Navegación automática en casos de éxito específicos
                    if (message == "Registro exitoso") {
                        navController.navigate("login")
                    } else if (message == "¡Compra exitosa!") {
                        navController.navigate("product_list") {
                            popUpTo("product_list") { inclusive = true }
                        }
                    }
                }
            }

            NavHost(navController = navController, startDestination = "menu") {

                // Menú Principal
                composable("menu") { MenuView { navController.navigate(it) } }

                // Login (Conectado a la API)
                composable("login") {
                    LoginView(
                        viewModel = viewModel,
                        onLoginSuccess = { role ->
                            if (role == "ADMIN") {
                                navController.navigate("admin")
                            } else {
                                navController.navigate("product_list")
                            }
                        },
                        onBackClicked = { navController.popBackStack() }
                    )
                }

                // Registro (Conectado a la API)
                composable("register") {
                    RegisterView(
                        viewModel = viewModel,
                        onBackClicked = { navController.popBackStack() }
                    )
                }

                // Lista de Productos (Home)
                composable("product_list") {
                    HomeView(
                        products = products,
                        onProductClicked = { productId -> navController.navigate("product/$productId") },
                        onAccountClicked = { /* TODO: Perfil */ },
                        onCartClicked = { navController.navigate("cart") },
                        onTitleClicked = { navController.navigate("product_list") }
                    )
                }

                // Detalle del Producto
                composable(
                    route = "product/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")
                    val product = products.find { it.id == productId }

                    if (product != null) {
                        ProductDetailView(
                            product = product,
                            onAddToCartClicked = { viewModel.addToCart(product) },
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                }

                // Carrito
                composable("cart") {
                    CartView(
                        cartItems = cartItems,
                        totalPrice = totalPrice,
                        onBackClicked = { navController.popBackStack() },
                        onIncrementItem = { viewModel.incrementItem(it) },
                        onDecrementItem = { viewModel.decrementItem(it) },
                        onRemoveItem = { viewModel.removeFromCart(it) },
                        onCheckoutClicked = { viewModel.checkout() }
                    )
                }

                // Panel Admin
                composable("admin") {
                    AdminView(
                        products = products,
                        viewModel = viewModel,
                        onAddSkinClicked = { navController.navigate("addSkin") },
                        onAddAgentClicked = { navController.navigate("addAgent") },
                        onAddCaseClicked = { navController.navigate("addCase") },
                        onAddSoundtrackClicked = { navController.navigate("addSoundtrack") },
                        onLogoutClicked = {
                            navController.navigate("menu") {
                                popUpTo("menu") { inclusive = true }
                            }
                        }
                    )
                }

                // Vistas de Agregar Productos (Admin)
                composable("addSkin") { AddSkinView(viewModel = viewModel, onBackClicked = { navController.popBackStack() }) }
                composable("addAgent") { AddAgentView(viewModel = viewModel, onBackClicked = { navController.popBackStack() }) }
                composable("addCase") { AddCaseView(viewModel = viewModel, onBackClicked = { navController.popBackStack() }) }
                composable("addSoundtrack") { AddSoundtrackView(viewModel = viewModel, onBackClicked = { navController.popBackStack() }) }
            }
        }
    }
}