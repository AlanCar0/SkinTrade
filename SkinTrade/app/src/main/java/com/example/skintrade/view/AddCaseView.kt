package com.example.skintrade.View

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.model.Case
import com.example.skintrade.viewmodel.SharedViewModel

@Composable
fun AddCaseView(viewModel: SharedViewModel, onBackClicked: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var featuredContent by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Agregar Caja", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
        OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("Imagen") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = featuredContent, onValueChange = { featuredContent = it }, label = { Text("Contenido Destacado") })

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            val newCase = Case(
                id = (viewModel.products.size + 1),
                name = name,
                price = price.toDoubleOrNull() ?: 0.0,
                image = image,
                description = description,
                featuredContent = featuredContent
            )
            viewModel.addProduct(newCase)
            onBackClicked()
        }) {
            Text("Guardar Caja")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBackClicked) { Text("Volver") }
    }
}