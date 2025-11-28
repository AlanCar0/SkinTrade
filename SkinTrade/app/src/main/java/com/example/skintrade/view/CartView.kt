package com.example.skintrade.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.skintrade.model.CartItem
import com.example.skintrade.data.remote.RetrofitClient
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    cartItems: List<CartItem>,
    totalPrice: Double,
    onBackClicked: () -> Unit,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    onRemoveItem: (CartItem) -> Unit,
    onCheckoutClicked: () -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) { Icon(Icons.Default.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FFC8)) }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D0D0D))
            )
        },
        bottomBar = {
            Row(
                modifier = Modifier.fillMaxWidth().background(Color.Black.copy(alpha = 0.8f)).padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(formatPrice(totalPrice), color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Button(
                    onClick = onCheckoutClicked,
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                    contentPadding = PaddingValues()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Brush.horizontalGradient(listOf(Color(0xFF00FFC8), Color(0xFFFFB300))))
                            .padding(horizontal = 24.dp, vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Pagar", color = Color(0xFF232526), fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { innerPadding ->
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                Text("Tu carrito está vacío", color = Color.Gray, fontSize = 18.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFF0D0D0D)),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems, key = { it.product.id }) { item ->
                    CartItemRow(item, onIncrement = { onIncrementItem(item) }, onDecrement = { onDecrementItem(item) }, onRemove = { onRemoveItem(item) })
                    Divider(color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(item: CartItem, onIncrement: () -> Unit, onDecrement: () -> Unit, onRemove: () -> Unit) {
    // URL IMAGEN
    val imageUrl = RetrofitClient.IMAGE_URL + item.product.image

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = imageUrl,
            contentDescription = item.product.name,
            modifier = Modifier.size(60.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text(formatPrice(item.product.price), color = Color.Gray, fontSize = 14.sp)
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = onDecrement, modifier = Modifier.size(32.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color.Gray), contentPadding = PaddingValues(0.dp)) {
                Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }
            Text("${item.quantity}", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
            Button(onClick = { if (item.quantity < 5) onIncrement() }, modifier = Modifier.size(32.dp), shape = CircleShape, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00FFC8)), contentPadding = PaddingValues(0.dp)) {
                Icon(Icons.Default.Add, contentDescription = "Añadir", tint = Color.Black)
            }
            IconButton(onClick = onRemove, modifier = Modifier.size(40.dp)) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = Color.Red.copy(alpha = 0.7f))
            }
        }
    }
}

private fun formatPrice(price: Double): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))
    return format.format(price)
}