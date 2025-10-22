package com.example.skintrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.skintrade.View.HomeView
import com.example.skintrade.View.LoginView
import com.example.skintrade.View.MenuView
import com.example.skintrade.View.RegisterView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "menu") {
                composable("menu") {
                    MenuView { route ->
                        navController.navigate(route)
                    }
                }
                composable("login") {
                    LoginView(
                        onLoginClicked = {
                            navController.navigate("home")
                        },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable("register") {
                    RegisterView(
                        onRegisterClicked = {
                            navController.navigate("login")
                        },
                        onBackClicked = { navController.popBackStack() }
                    )
                }
                composable("home") {
                    HomeView(
                        onAccountClicked = { /* TODO: Navegar a la pantalla de cuenta */ },
                        onCartClicked = { /* TODO: Navegar a la pantalla del carrito */ },
                        onTitleClicked = { navController.navigate("home") }
                    )
                }
            }
        }
    }
}
