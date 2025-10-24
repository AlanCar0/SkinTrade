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
        Button(
            onClick = onLogoutClicked,
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Cerrar sesión")
        }
        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(products) { product ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("Nombre: ${product.name}", fontSize = 18.sp)
                            Text("Precio: $${"%,d".format(product.price).replace(",", ".")}", fontSize = 16.sp)
                            Text("Categoría: ${product.description}", fontSize = 14.sp)
                        }
                        Column(
                            horizontalAlignment = Alignment.End,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { /* futuras vainas aqui dentro */ },
                                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Eliminar")
                            }
                            Button(
                                onClick = { /* despues agregar las vainas aca */ },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("Editar")
                            }
                        }
                    }
                }
            }
        }
    }
}