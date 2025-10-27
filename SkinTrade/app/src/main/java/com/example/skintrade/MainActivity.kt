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
import com.example.skintrade.View.*
import com.example.skintrade.viewmodel.SharedViewModel

class MainActivity : ComponentActivity() {
    
    private val viewModel: SharedViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current

            val products = viewModel.products
            val cartItems by viewModel.cartItems.collectAsState()
            val totalPrice by viewModel.totalPrice.collectAsState()
            val uiEvent by viewModel.uiEvents.collectAsState()

            LaunchedEffect(uiEvent) {
                uiEvent?.let {
                    if (it == "¡Compra exitosa!") {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                        navController.navigate("home") { 
                            popUpTo("home") { inclusive = true } 
                        }
                    } else {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                    viewModel.clearUiEvent()
                }
            }

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") { MenuView { navController.navigate(it) } }
                composable("login") { LoginView({ navController.navigate("home") }, { navController.popBackStack() }) }
                
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
                        products = products,
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
                        onRemoveItem = { viewModel.removeFromCart(it) },
                        onCheckoutClicked = { viewModel.checkout() }
                    )
                }
            }
        }
    }
}