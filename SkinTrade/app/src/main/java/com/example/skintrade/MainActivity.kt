package com.example.skintrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skintrade.Model.Product
import com.example.skintrade.View.*
import com.example.skintrade.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    
    private val viewModel: SharedViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // Usa los nuevos nombres del ViewModel
            val products = viewModel.products
            val cartItems by viewModel.cartItems.collectAsState()
            val totalPrice by viewModel.totalPrice.collectAsState()

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") { MenuView { navController.navigate(it) } }
                composable("login") {
                    LoginView(
                        onLoginClicked = { role ->
                            if (role == "admin") {
                                // Navegar al Backoffice
                                navController.navigate("admin")
                            } else if (role=="home") {
                                // Navegar a vista de usuario normal
                                navController.navigate("home")
                            }
                        },
                        onBackClicked = {
                            navController.popBackStack()
                        }
                    )
                }
                
                composable("register") { 
                    RegisterView(
                        onRegisterClicked = { _, _ -> 
                            navController.navigate("login")
                        },
                        onBackClicked = { navController.popBackStack() }
                    )
                }

                composable("home") {
                    HomeView(
                        products = products, // Pasa la lista de `Product`
                        onProductClicked = { navController.navigate("product/$it") },
                        onAccountClicked = { /* TODO */ },
                        onCartClicked = { navController.navigate("cart") },
                        onTitleClicked = { navController.navigate("home") }
                    )
                }

                composable(
                    route = "product/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")
                    // Busca por `id` en lugar de `id_p`
                    val product = products.find { it.id == productId }
                    if (product != null) {
                        ProductDetailView(
                            product = product,
                            onAddToCartClicked = { viewModel.addToCart(product) }, 
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                }

                composable("cart") {
                    CartView(
                        cartItems = cartItems,
                        totalPrice = totalPrice,
                        onBackClicked = { navController.popBackStack() },
                        onIncrementItem = { viewModel.incrementItem(it) },
                        onDecrementItem = { viewModel.decrementItem(it) },
                        onRemoveItem = { viewModel.removeFromCart(it) }
                    )
                }
                composable ("admin"){
                    AdminView(
                        products = viewModel.products,
                        viewModel = viewModel,
                        onLogoutClicked = { navController.popBackStack() }
                    )}

            }
        }
    }
}