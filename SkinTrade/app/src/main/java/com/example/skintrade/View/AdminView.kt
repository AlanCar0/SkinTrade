package com.example.skintrade.View

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.Model.Product
import com.example.skintrade.viewmodel.SharedViewModel

@Composable
fun AdminView(
    products: List<Product>,
    viewModel: SharedViewModel,
    onAddSkinClicked: () -> Unit,
    onAddAgentClicked: () -> Unit,
    onAddCaseClicked: () -> Unit,
    onAddSoundtrackClicked: () -> Unit,
    onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            "Panel de Administración",
            fontSize = 26.sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón cerrar sesión
        Button(
            onClick = onLogoutClicked,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Cerrar sesión")
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Botones para agregar productos
        Text("Agregar nuevo producto", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = onAddSkinClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Skin")
        }
        Button(onClick = onAddAgentClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Agente")
        }
        Button(onClick = onAddCaseClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Caja")
        }
        Button(onClick = onAddSoundtrackClicked, modifier = Modifier.fillMaxWidth()) {
            Text("Agregar Soundtrack")
        }

        Spacer(modifier = Modifier.height(20.dp))

        Divider()

        Spacer(modifier = Modifier.height(8.dp))
        Text("Lista de productos", fontSize = 20.sp)

        // Lista de productos
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("Nombre: ${product.name}", fontSize = 18.sp)
                        Text("Precio: $${product.price}", fontSize = 16.sp)
                        Text("Tipo: ${product::class.simpleName}", fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { viewModel.removeProduct(product) },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text("Eliminar")
                        }
                    }
                }
            }
        }
    }
}