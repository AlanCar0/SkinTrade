package com.example.skintrade.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.skintrade.viewmodel.SharedViewModel

@Composable
fun RegisterView(viewModel: SharedViewModel, onBackClicked: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // Campos de Texto
        InputText(value = nombre, onValueChange = { nombre = it }, label = "Nombre Completo")
        InputText(value = email, onValueChange = { email = it }, label = "Email")
        InputText(value = rut, onValueChange = { rut = it }, label = "RUT (Ej: 12345678-9)")
        InputText(value = numero, onValueChange = { numero = it }, label = "Teléfono")

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 8.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray
            )
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (nombre.isNotBlank() && email.isNotBlank() && rut.isNotBlank() && password.isNotBlank()) {
                    viewModel.register(nombre, email, rut, numero, password) {
                        // El ViewModel maneja el Toast de éxito
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.horizontalGradient(listOf(Color(0xFF00FFC8), Color(0xFFFFB300)))
                ),
                contentAlignment = Alignment.Center
            ) {
                Text("Registrarse", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onBackClicked,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp)
        ) {
            Text("Volver", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun InputText(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 4.dp),
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            focusedIndicatorColor = Color(0xFF1E88E5),
            unfocusedIndicatorColor = Color.Gray
        )
    )
}