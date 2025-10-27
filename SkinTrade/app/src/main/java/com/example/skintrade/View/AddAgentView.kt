package com.example.skintrade.View

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.Model.Agent
import com.example.skintrade.viewmodel.SharedViewModel

@Composable
fun AddAgentView(viewModel: SharedViewModel, onBackClicked: () -> Unit) {
    var name by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var image by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Agregar Agente", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre") })
        OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Precio") })
        OutlinedTextField(value = image, onValueChange = { image = it }, label = { Text("Imagen") })
        OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
        OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Categoría") })

        Spacer(modifier = Modifier.height(12.dp))
        Button(onClick = {
            val newAgent = Agent(
                id = (viewModel.products.size + 1),
                name = name,
                price = price.toDoubleOrNull() ?: 0.0,
                image = image,
                description = description,
                category = category

            )
            viewModel.addProduct(newAgent)
            onBackClicked()
        }) {
            Text("Guardar Agente")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onBackClicked) {
            Text("Volver")
        }
    }
}