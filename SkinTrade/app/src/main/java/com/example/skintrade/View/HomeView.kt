package com.example.skintrade.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.Model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    products: List<Product>,
    onProductClicked: (Int) -> Unit,
    onAccountClicked: () -> Unit,
    onCartClicked: () -> Unit,
    onTitleClicked: () -> Unit
) {
    val context = LocalContext.current

    var menuExpanded by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf("Todos") }
    val filteredProducts = remember(products, selectedFilter) {
        when (selectedFilter) {
            "Skins" -> products.filterIsInstance<Skin>()
            "Agentes" -> products.filterIsInstance<Agent>()
            "Cajas" -> products.filterIsInstance<Case>()
            "Soundtracks" -> products.filterIsInstance<Soundtrack>()
            else -> products // "Todos"
        }
    }

    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { TextButton(onClick = onTitleClicked) { Text("SkinTrade", color = Color.White) } },
                navigationIcon = {
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(Icons.Default.Menu, contentDescription = "Filtrar productos")
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        val filterOptions = listOf("Todos", "Skins", "Agentes", "Cajas", "Soundtracks")
                        filterOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    selectedFilter = option
                                    menuExpanded = false
                                }
                            )
                        }
                    }
                },
                actions = { 
                    IconButton(onClick = onAccountClicked) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Cuenta")
                    }
                    IconButton(onClick = onCartClicked) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                    }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF0D0D0D))
        ) {
            if (filteredProducts.isNotEmpty()) {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(items = filteredProducts, key = { it.id }) { product ->
                        Card(
                            onClick = { onProductClicked(product.id) },
                            modifier = Modifier.padding(bottom = 16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray.copy(alpha = 0.2f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val imageResId = remember(product.image) {
                                    context.getResources().getIdentifier(product.image, "drawable", context.packageName)
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(150.dp)
                                        .background(Color.Black)
                                ) {
                                    if (imageResId != 0) {
                                        Image(
                                            painter = painterResource(id = imageResId),
                                            contentDescription = "Imagen de ${product.name}",
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Fit
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Default.Info,
                                            contentDescription = "Imagen no encontrada",
                                            modifier = Modifier.size(64.dp).align(Alignment.Center),
                                            tint = Color.Red
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(8.dp))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp, vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = product.name,
                                        color = Color.White,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    Text(
                                        text = "$${product.price}", // CORREGIDO: Sin división
                                        color = Color(0xFF00FFC8),
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                Text(
                    text = "No hay productos que coincidan con el filtro.",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}