package com.example.ospedidos.ui.theme.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ospedidos.model.modules.Modulo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    navController: NavController,
    modulesState: State<List<Modulo>>
) {
    val modules = modulesState.value

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Os Pedidos - MODULOS",
                modifier = Modifier
                    .padding(vertical = 16.dp),
            )
        }

        // Crie um item para cada módulo na lista
        itemsIndexed(modules) { index, modulo ->
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    // Aqui você pode lidar com a navegação ou outra lógica com base no módulo
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = modulo.nomeModulo)
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






/*@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ModuleScreenPreview() {
    val navController = rememberNavController()
    ModuleScreen(navController = navController,  )

}*/
