package com.example.ospedidos.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ospedidos.presentation.model.product.Product
import com.example.ospedidos.utils.CustomAlertDialog
import com.example.ospedidos.utils.SharedPreferenceManager
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController,
    onProductClick: (String, String, String) -> Unit
) {
    var productName: String
    val context = LocalContext.current

    // Recupere a lista de produtos do SharedPreferences
    val productList = SharedPreferenceManager.getProductList(context)

    var totalValue by remember { mutableStateOf(0.0) }
    var isCustomAlertDialogVisible by remember { mutableStateOf(false) }
    var selectedProduct: Product? by remember { mutableStateOf(null) }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Os Pedidos - PRODUTOS",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Total: " + formatCurrency(0.0),
                fontSize = 24.sp,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center


            )
        }

        item {
            Text(
                text = "Débito - Crédito",
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        // Lista de botões dos produtos
        itemsIndexed(productList.orEmpty()) { index, product ->
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    selectedProduct = product
                    isCustomAlertDialogVisible = true
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = product.item)
                Text(text = "R$${product.preco}", modifier = Modifier.padding(start = 8.dp))
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.popBackStack() }, // Navegar de volta
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Voltar")
            }
        }
    }
    if (isCustomAlertDialogVisible && selectedProduct != null) {
        CustomAlertDialog(
            productName = selectedProduct!!.item,
            productPrice = selectedProduct!!.preco,
            onQuantitySelected = { quantity -> /* Lógica para quantidade selecionada */ },
            onCloseClicked = {
                // Lógica para fechar o CustomAlertDialog
                isCustomAlertDialogVisible = false
            }
        )
    }

}
private fun formatCurrency(value: Double): String {
    val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    return currencyFormat.format(value)
}
