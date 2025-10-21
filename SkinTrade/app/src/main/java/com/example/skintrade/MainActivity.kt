package com.example.skintrade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.skintrade.View.MenuView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MenuView { destino ->
                when (destino) {
                    "home" -> println("Ir a inicio")
                    "catalogo" -> println("Ir al catálogo")
                    "login" -> println("Ir al login")
                }
            }
        }
    }
}
