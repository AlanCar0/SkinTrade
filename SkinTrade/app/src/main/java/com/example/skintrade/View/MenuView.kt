package com.example.skintrade.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuView(onNavigate: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo / título
        Text(
            text = "SkinTrade",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 40.dp, bottom = 24.dp)
        )

        // (Aquí estaba la imagen, la eliminamos)
        Spacer(modifier = Modifier.height(24.dp))

        // Botones del menú
        MenuButton("Inicio de sesión") { onNavigate("login") }
        MenuButton("Registrarse") { onNavigate("register") }

        Spacer(modifier = Modifier.height(24.dp))

        // Footer
        Text(
            text = "© 2025 SkinTrade - Todos los derechos reservados.",
            color = Color.Gray,
            fontSize = 12.sp
        )
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .padding(vertical = 8.dp)
            .height(50.dp),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFF00FFC8), Color(0xFFFFB300))
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                color = Color(0xFF232526),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
