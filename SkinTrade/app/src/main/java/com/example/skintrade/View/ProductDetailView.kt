package com.example.skintrade.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.Model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailView(producto: Productos, onBackClicked: () -> Unit) {
    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { Text(producto.nombre, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FFC8))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D0D0D))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .background(Color(0xFF0D0D0D))
                .verticalScroll(rememberScrollState()), // Permite hacer scroll si el contenido es muy largo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val imageResId = remember(producto.imagen) {
                context.getResources().getIdentifier(producto.imagen, "drawable", context.packageName)
            }

            // Imagen del producto
            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Imagen de ${producto.nombre}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- MOSTRAR LA DESCRIPCIÓN ---
            Text(
                text = producto.descripcion,
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            Divider(color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            
            // Detalles específicos según el tipo de producto
            when (producto) {
                is Skin -> {
                    DetailRow("Categoría:", producto.cat)
                    DetailRow("Estado:", producto.estado)
                }
                is Agente -> {
                    DetailRow("Facción:", producto.cat)
                }
                is Caja -> {
                    DetailRow("Contenido Destacado:", producto.cont)
                }
                is Soundtrack -> {
                    DetailRow("Autor:", producto.autor)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Precio y botón de compra
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Precio: $${producto.precio}", color = Color(0xFF00FFC8), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                Button(onClick = { /* TODO: Lógica para añadir al carrito */ }) {
                    Text("Añadir al Carrito")
                }
            }
        }
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Text(label, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.weight(1f))
        Text(value, color = Color.White, modifier = Modifier.weight(1f))
    }
}
