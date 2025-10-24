package com.example.skintrade.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginView(
    onLoginClicked: (String) -> Unit, // 🔹 Ahora recibe el tipo de usuario ("admin" o "user")
    onBackClicked: () -> Unit
) {
    var steamUsername by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Iniciar Sesión",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 🔸 Campo de usuario
        OutlinedTextField(
            value = steamUsername,
            onValueChange = { steamUsername = it; usernameError = null },
            label = { Text("Usuario de Steam") },
            modifier = Modifier.fillMaxWidth(0.8f),
            isError = usernameError != null,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )
        usernameError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔸 Campo de contraseña
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = null },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f),
            isError = passwordError != null,
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.Gray,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                errorIndicatorColor = MaterialTheme.colorScheme.error,
                errorLabelColor = MaterialTheme.colorScheme.error
            )
        )
        passwordError?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // 🔹 BOTÓN DE LOGIN
        Button(
            onClick = {
                var isValid = true
                if (steamUsername.isBlank()) {
                    usernameError = "El nombre de usuario no puede estar vacío."
                    isValid = false
                }
                if (password.isBlank()) {
                    passwordError = "La contraseña no puede estar vacía."
                    isValid = false
                }

                if (isValid) {
                    // ✅ Verificamos tipo de usuario
                    if (steamUsername == "admin" && password == "1234") {
                        onLoginClicked("admin") // → Navega al backoffice
                    } else {
                        onLoginClicked("home") // → Navega a vista normal
                    }
                }
            },
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
                    text = "Iniciar Sesión",
                    color = Color(0xFF232526),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 BOTÓN VOLVER
        Button(
            onClick = onBackClicked,
            shape = MaterialTheme.shapes.medium,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(vertical = 8.dp)
                .height(50.dp)
        ) {
            Text(text = "Volver al Menú", fontSize = 18.sp, color = Color.White)
        }
    }
}
