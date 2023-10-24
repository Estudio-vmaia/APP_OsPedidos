package com.example.ospedidos.ui.theme.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ospedidos.utils.SharedPreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderScreen(
    navController: NavController
) {
    val context = LocalContext.current

    // Recupere a lista de produtos do SharedPreferences
    val productList = SharedPreferenceManager.getProductList(context)

    var totalValue by remember { mutableStateOf(0.0) }
    var isTotalValueVisible by remember { mutableStateOf(false) }

    // Calcule o valor total dos produtos
    productList?.forEach { product ->
        val productPrice = product.preco.toDoubleOrNull()
        if (productPrice != null) {
            totalValue
        }
    }

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
            if (isTotalValueVisible) {
                Text(
                    text = "Total: R$$totalValue",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable { isTotalValueVisible = !isTotalValueVisible }
                )
            } else {
                Text(
                    text = "Total: R$$totalValue",
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .clickable { isTotalValueVisible = !isTotalValueVisible }
                        .padding(16.dp)
                )
            }
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
                    // Lógica para adicionar o produto ao carrinho ou processar o pedido
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
}
