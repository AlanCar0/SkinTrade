package com.example.skintrade.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skintrade.model.*
import com.example.skintrade.data.remote.RetrofitClient
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    products: List<Product>,
    onProductClicked: (Int) -> Unit,
    onAccountClicked: () -> Unit,
    onCartClicked: () -> Unit,
    onTitleClicked: () -> Unit
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Todos") }

    val filteredProducts = remember(products, selectedFilter) {
        when (selectedFilter) {
            "Skins" -> products.filterIsInstance<Skin>()
            "Agentes" -> products.filterIsInstance<Agent>()
            "Cajas" -> products.filterIsInstance<Case>()
            "Soundtracks" -> products.filterIsInstance<Soundtrack>()
            else -> products
        }
    }

    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { TextButton(onClick = onTitleClicked) { Text("SkinTrade", color = Color.White) } },
                navigationIcon = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Filtro")
                    }
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        listOf("Todos", "Skins", "Agentes", "Cajas", "Soundtracks").forEach { option ->
                            DropdownMenuItem(text = { Text(option) }, onClick = { selectedFilter = option; menuExpanded = false })
                        }
                    }
                },
                actions = {
                    IconButton(onClick = onAccountClicked) { Icon(Icons.Default.AccountCircle, "Cuenta") }
                    IconButton(onClick = onCartClicked) { Icon(Icons.Default.ShoppingCart, "Carrito") }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color(0xFF00FFC8),
                    navigationIconContentColor = Color(0xFF00FFC8)
                )
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFF0D0D0D))) {
            if (filteredProducts.isNotEmpty()) {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(items = filteredProducts, key = { it.id }) { product ->
                        Card(
                            onClick = { onProductClicked(product.id) },
                            modifier = Modifier.padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.2f))
                        ) {
                            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

                                // --- CAMBIO: USAR COIL PARA IMAGEN WEB ---
                                val imageUrl = RetrofitClient.IMAGE_URL + product.image

                                Box(modifier = Modifier.fillMaxWidth().height(150.dp).background(Color.Black)) {
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = product.name,
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit,
                                        // Puedes agregar 'error = painterResource(R.drawable.error_icon)' si quieres
                                    )
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(product.name, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                                    Text(formatPrice(product.price), color = Color(0xFF00FFC8), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            } else {
                Text("Cargando productos...", color = Color.White, modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

private fun formatPrice(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(price)
}