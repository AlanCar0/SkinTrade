package com.example.skintrade.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
fun HomeView(onAccountClicked: () -> Unit, onCartClicked: () -> Unit, onTitleClicked: () -> Unit) {

    val context = LocalContext.current

    val productos = remember {
        val skins = loadProductosFromAssets(context, "skins.json") ?: emptyList()
        val agentes = loadProductosFromAssets(context, "agentes.json") ?: emptyList()
        val cajas = loadProductosFromAssets(context, "cajas.json") ?: emptyList()
        val soundtracks = loadProductosFromAssets(context, "soundtracks.json") ?: emptyList()

        skins + agentes + cajas + soundtracks
    }

    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = {
                    TextButton(onClick = onTitleClicked) {
                        Text("SkinTrade", color = Color.White)
                    }
                },
                actions = {
                    IconButton(onClick = onAccountClicked) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Cuenta")
                    }
                    IconButton(onClick = onCartClicked) {
                        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito de compras")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0D0D0D),
                    titleContentColor = Color.White,
                    actionIconContentColor = Color(0xFF00FFC8)
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
            if (productos.isNotEmpty()) {
                LazyColumn(contentPadding = PaddingValues(16.dp)) {
                    items(productos) { producto ->
                        // Contenedor para cada producto con imagen y nombre
                        Column(
                            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val imageResId = remember(producto.imagen) {
                                context.getResources().getIdentifier(producto.imagen, "drawable", context.packageName)
                            }

                            // Contenedor de la imagen
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(150.dp) // Altura fija para el contenedor de la imagen
                                    .background(Color.Black) // Fondo negro por si la imagen no carga
                            ) {
                                if (imageResId != 0) {
                                    Image(
                                        painter = painterResource(id = imageResId),
                                        contentDescription = "Imagen de ${producto.nombre}",
                                        modifier = Modifier.fillMaxSize(),
                                        contentScale = ContentScale.Fit // <-- CAMBIO CLAVE: Escala la imagen para que quepa entera
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Error,
                                        contentDescription = "Imagen no encontrada",
                                        modifier = Modifier.size(64.dp).align(Alignment.Center),
                                        tint = Color.Red
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            // Nombre del producto
                            Text(
                                text = producto.nombre, // <-- CAMBIO CLAVE: Solo muestra el nombre
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            } else {
                Text(
                    text = "No se han podido cargar los productos.",
                    color = Color.White
                )
            }
        }
    }
}
