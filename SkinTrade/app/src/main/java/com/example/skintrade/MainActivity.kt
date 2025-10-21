package com.example.skintrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                            // Lógica para cuando se hace clic en el botón de inicio de sesión
                            println("Botón de inicio de sesión presionado")
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
            }
        }
    }
}
