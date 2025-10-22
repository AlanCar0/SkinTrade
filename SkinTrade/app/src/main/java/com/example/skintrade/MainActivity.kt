package com.example.skintrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.skintrade.Model.loadProductosFromAssets
import com.example.skintrade.View.HomeView
import com.example.skintrade.View.LoginView
import com.example.skintrade.View.MenuView
import com.example.skintrade.View.ProductDetailView
import com.example.skintrade.View.RegisterView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            // 1. Cargar la lista completa de productos UNA SOLA VEZ aquí
            val productos = remember {
                val skins = loadProductosFromAssets(this, "skins.json") ?: emptyList()
                val agentes = loadProductosFromAssets(this, "agentes.json") ?: emptyList()
                val cajas = loadProductosFromAssets(this, "cajas.json") ?: emptyList()
                val soundtracks = loadProductosFromAssets(this, "soundtracks.json") ?: emptyList()
                skins + agentes + cajas + soundtracks
            }

            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    MenuView { route ->
                        navController.navigate(route)
                    }
                }
                composable("login") {
                    LoginView(
                        onLoginClicked = { navController.navigate("home") },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable("register") {
                    RegisterView(
                        onRegisterClicked = { navController.navigate("login") },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable("home") {
                    HomeView(
                        productos = productos, // 2. Pasar la lista de productos a la HomeView
                        onProductClicked = { productId ->
                            // 3. Navegar a la pantalla de detalle pasando solo el ID
                            navController.navigate("product/$productId")
                        },
                        onAccountClicked = { /* TODO */ },
                        onCartClicked = { /* TODO */ },
                        onTitleClicked = { navController.navigate("home") }
                    )
                }
                // 4. Nueva ruta para la pantalla de detalle
                composable(
                    route = "product/{productId}",
                    arguments = listOf(navArgument("productId") { type = NavType.IntType })
                ) { backStackEntry ->
                    val productId = backStackEntry.arguments?.getInt("productId")
                    // 5. Buscar el producto en la lista maestra y mostrar los detalles
                    val producto = productos.find { it.id_p == productId }
                    if (producto != null) {
                        ProductDetailView(
                            producto = producto,
                            onBackClicked = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
