package com.example.skintrade.View

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.skintrade.Model.CartItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartView(
    cartItems: List<CartItem>,
    totalPrice: Int,
    onBackClicked: () -> Unit,
    onIncrementItem: (CartItem) -> Unit,
    onDecrementItem: (CartItem) -> Unit,
    onRemoveItem: (CartItem) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        containerColor = Color(0xFF0D0D0D),
        topBar = {
            TopAppBar(
                title = { Text("Mi Carrito", color = Color.White) }, // TRADUCIDO
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = Color(0xFF00FFC8)) // TRADUCIDO
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF0D0D0D))
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
<<<<<<< Updated upstream
                    text = "Total: $${"%,d".format(totalPrice).replace(",", ".")}",
=======
                    text = "Total: $${"%,d".format(totalPrice)}",
>>>>>>> Stashed changes
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Pago realizado")
                        }
                        cartItems.forEach { onRemoveItem(it) } // Vacía el carrito
                    },
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
                Text("Tu carrito está vacío", color = Color.Gray, fontSize = 18.sp) // TRADUCIDO
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(innerPadding).background(Color(0xFF0D0D0D)),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(cartItems, key = { it.product.id }) { item ->
                    CartItemRow(
                        item = item,
                        onIncrement = { onIncrementItem(item) },
                        onDecrement = { onDecrementItem(item) },
                        onRemove = { onRemoveItem(item) }
                    )
                    HorizontalDivider(color = Color.DarkGray)
                }
            }
        }
    }
}

@Composable
private fun CartItemRow(
    item: CartItem,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    val context = LocalContext.current
    val imageResId = remember(item.product.image) {
        context.getResources().getIdentifier(item.product.image, "drawable", context.packageName)
    }

    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (imageResId != 0) {
            Image(painter = painterResource(id = imageResId), contentDescription = item.product.name, modifier = Modifier.size(60.dp))
        } else {
            Box(modifier = Modifier.size(60.dp).background(Color.DarkGray))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, color = Color.White, fontWeight = FontWeight.SemiBold)
            Text("$${item.product.price}", color = Color.Gray, fontSize = 14.sp) // CORREGIDO
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = onDecrement,
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        brush = Brush.horizontalGradient(colors = listOf(Color(0xFF00FFC8), Color(0xFFFFB300)))
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("-", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color(0xFF232526))
                }
            }
            
            Text("${item.quantity}", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 8.dp))
            
            Button(
                onClick = { if (item.quantity < 3) onIncrement() },
                modifier = Modifier.size(32.dp),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize().background(
                        brush = Brush.horizontalGradient(colors = listOf(Color(0xFF00FFC8), Color(0xFFFFB300)))
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Añadir uno", tint = Color(0xFF232526)) // TRADUCIDO
                }
            }
        }

        IconButton(onClick = onRemove, modifier = Modifier.size(40.dp)) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar del carrito", tint = Color.Red.copy(alpha = 0.7f)) // TRADUCIDO
        }
    }
}