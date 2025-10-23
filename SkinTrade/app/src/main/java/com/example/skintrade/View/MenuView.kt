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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuView(onNavigate: (String) -> Unit) {
    val footerText = "SkinTrade es un mercado digital especializado en la compra y venta de skins y artículos virtuales para jugadores de toda Latinoamérica.\nNuestra misión es ofrecer una plataforma segura, rápida y confiable donde los usuarios puedan comercializar sus productos con total transparencia y soporte local."
    val warningText = "ADVERTENCIA: Las cuentas baneadas o con restricciones de intercambio no pueden vender ni intercambiar armas dentro de este mercado.\nSkinTrade se reserva el derecho de limitar o suspender el acceso a usuarios que incumplan esta política."

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Contenedor para el contenido principal, que ocupa el espacio sobrante
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "SkinTrade",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            MenuButton("Inicio de sesión") { onNavigate("login") }
            MenuButton("Registrarse") { onNavigate("register") }
        }

        // Footer, que queda anclado en la parte inferior
        Text(
            text = footerText,
            color = Color.Gray,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp)) // Espacio entre los dos textos

        // Texto de advertencia
        Text(
            text = warningText,
            color = Color.DarkGray, // Un color un poco más tenue
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
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
