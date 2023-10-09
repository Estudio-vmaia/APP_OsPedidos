package com.example.ospedidos.ui.theme.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    navController: NavController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Os Pedidos - EVENTOS"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { navController.navigate("storeCategoryScreen")},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Quermesse 2023")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "QUERMESSE 2027")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Voltar")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ModuleScreenPreview() {
    val navController = rememberNavController()
    ModuleScreen(navController = navController)

}
