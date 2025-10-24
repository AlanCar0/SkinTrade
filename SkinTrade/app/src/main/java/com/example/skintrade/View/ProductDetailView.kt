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
import androidx.compose.ui.graphics.Brush
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
fun ProductDetailView(product: Product, onBackClicked: () -> Unit, onAddToCartClicked: () -> Unit) {

    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { Text(product.name, color = Color.White) },
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val imageResId = remember(product.image) {
                context.getResources().getIdentifier(product.image, "drawable", context.packageName)
            }

            if (imageResId != 0) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Imagen de ${product.name}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = product.description,
                color = Color.LightGray,
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = Color.DarkGray)
            Spacer(modifier = Modifier.height(16.dp))
            
            when (product) {
                is Skin -> {
                    DetailRow("Categoría:", product.category)
                    DetailRow("Condición:", product.condition)
                }
                is Agent -> {
                    DetailRow("Facción:", product.category)
                }
                is Case -> {
                    DetailRow("Contenido Destacado:", product.featuredContent)
                }
                is Soundtrack -> {
                    DetailRow("Autor:", product.author)
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Precio: $${"%,d".format(product.price).replace(",", ".")}",
                    color = Color(0xFF00FFC8),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                // BOTÓN CON NUEVO ESTILO
                Button(
                    onClick = onAddToCartClicked,
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFF00FFC8), Color(0xFFFFB300))
                                )
                            )
                            .padding(horizontal = 16.dp, vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Añadir al Carrito", color = Color(0xFF232526), fontWeight = FontWeight.Bold)
                    }
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