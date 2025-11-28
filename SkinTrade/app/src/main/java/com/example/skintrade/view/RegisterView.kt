package com.example.skintrade.View

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.viewmodel.SharedViewModel

@Composable
fun RegisterView(viewModel: SharedViewModel, onBackClicked: () -> Unit) {
    // Estados de los campos
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var rut by remember { mutableStateOf("") }
    var numero by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Estados de errores
    var nombreError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var rutError by remember { mutableStateOf<String?>(null) }
    var numeroError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Scroll por si el teclado tapa campos
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Crear Cuenta", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        // --- CAMPOS DE FORMULARIO ---

        ValidatedTextField(
            value = nombre,
            onValueChange = { nombre = it; nombreError = null },
            label = "Nombre Completo",
            error = nombreError
        )

        ValidatedTextField(
            value = email,
            onValueChange = { email = it; emailError = null },
            label = "Email",
            error = emailError,
            keyboardType = KeyboardType.Email
        )

        ValidatedTextField(
            value = rut,
            onValueChange = { rut = it; rutError = null },
            label = "RUT (Ej: 12345678-9)",
            error = rutError
        )

        ValidatedTextField(
            value = numero,
            onValueChange = { numero = it; numeroError = null },
            label = "Teléfono (+569...)",
            error = numeroError,
            keyboardType = KeyboardType.Phone
        )

        // Campo Password (Manual porque tiene VisualTransformation)
        OutlinedTextField(
            value = password,
            onValueChange = { password = it; passwordError = null },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError != null,
            modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        if (passwordError != null) {
            Text(passwordError!!, color = MaterialTheme.colorScheme.error, fontSize = 12.sp, modifier = Modifier.fillMaxWidth(0.8f))
        }

        Spacer(modifier = Modifier.height(24.dp))

        // --- BOTÓN REGISTRAR ---
        Button(
            onClick = {
                // 1. Limpiar errores previos
                nombreError = null
                emailError = null
                rutError = null
                numeroError = null
                passwordError = null

                var isValid = true

                // 2. Validaciones
                if (nombre.isBlank()) {
                    nombreError = "El nombre es obligatorio"
                    isValid = false
                }

                if (email.isBlank()) {
                    emailError = "El email es obligatorio"
                    isValid = false
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailError = "Formato de email inválido"
                    isValid = false
                }

                if (rut.isBlank()) {
                    rutError = "El RUT es obligatorio"
                    isValid = false
                } else if (!isValidRut(rut)) {
                    rutError = "RUT inválido (Dígito verificador incorrecto)"
                    isValid = false
                }

                if (numero.isBlank()) {
                    numeroError = "El teléfono es obligatorio"
                    isValid = false
                } else if (numero.length < 8) {
                    numeroError = "El número es muy corto"
                    isValid = false
                }

                if (password.isBlank()) {
                    passwordError = "La contraseña es obligatoria"
                    isValid = false
                } else if (password.length < 6) {
                    passwordError = "Mínimo 6 caracteres"
                    isValid = false
                }

                // 3. Enviar si todo está bien
                if (isValid) {
                    viewModel.register(nombre, email, rut, numero, password) {
                        // El ViewModel maneja la navegación o mensaje de éxito
                    }
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f).height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            contentPadding = PaddingValues()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
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

// --- COMPONENTE REUTILIZABLE PARA INPUTS ---
@Composable
fun ValidatedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    error: String?,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = error != null,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            modifier = Modifier.fillMaxWidth(0.8f).padding(vertical = 4.dp),
            colors = TextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedIndicatorColor = Color(0xFF1E88E5),
                unfocusedIndicatorColor = Color.Gray,
                errorIndicatorColor = MaterialTheme.colorScheme.error
            )
        )
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.fillMaxWidth(0.8f).padding(start = 4.dp)
            )
        }
    }
}

// --- LÓGICA DE VALIDACIÓN DE RUT (Módulo 11) ---
fun isValidRut(rut: String): Boolean {
    // 1. Limpieza básica (quitar puntos y guión)
    val cleanRut = rut.replace(".", "").replace("-", "").trim().uppercase()

    // Validar formato (números + K al final) y largo mínimo
    if (!cleanRut.matches(Regex("^[0-9]+[0-9K]$")) || cleanRut.length < 2) return false

    try {
        // Separar cuerpo y dígito verificador
        val cuerpo = cleanRut.substring(0, cleanRut.length - 1)
        val dv = cleanRut.last()

        // 2. Algoritmo Módulo 11
        var suma = 0
        var multiplicador = 2

        // Recorrer el cuerpo de atrás hacia adelante
        for (i in cuerpo.length - 1 downTo 0) {
            suma += cuerpo[i].toString().toInt() * multiplicador
            multiplicador++
            if (multiplicador > 7) multiplicador = 2
        }

        val resto = 11 - (suma % 11)

        val dvCalculado = when (resto) {
            11 -> '0'
            10 -> 'K'
            else -> resto.toString()[0]
        }

        return dv == dvCalculado

    } catch (e: Exception) {
        return false
    }
}