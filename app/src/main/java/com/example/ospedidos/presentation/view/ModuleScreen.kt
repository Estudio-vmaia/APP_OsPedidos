package com.example.ospedidos.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ospedidos.presentation.model.event.Event
import com.example.ospedidos.presentation.model.modules.Modulo
import com.example.ospedidos.presentation.model.modules.ModuloResponse
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModuleScreen(
    navController: NavController,
    modulesState: State<List<Modulo>>,
    onModuleSelected: (String) -> Unit
) {
    val modules = modulesState.value
    var eventList by remember { mutableStateOf(emptyList<Event>()) }


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
                    // Use o nome do módulo como rótulo do botão
                    onModuleSelected(modulo.nomeModulo)
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
                Text(text = "SAIR")
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun ModuleScreenPreview() {
    val navController = rememberNavController()

    val json = """
        {
            "arrayName": "modulos",
            "arrayColunas": "id,idModulo,idCliente,cliente,slug,embed,ativo,validade,dataCadastro",
            "arrayKeys": 2,
            "modulos": [
                {
                    "nomeModulo": "Eventos",
                    "urlModulo": "eventos",
                    "id": "1",
                    "idModulo": "1",
                    "idCliente": "11",
                    "cliente": "Matriz",
                    "slug": "matriz",
                    "embed": "matris",
                    "ativo": "1",
                    "validade": "20231231",
                    "dataCadastro": "20230907"
                },
                {
                    "nomeModulo": "Loja",
                    "urlModulo": "loja",
                    "id": "2",
                    "idModulo": "2",
                    "idCliente": "11",
                    "cliente": "Matriz",
                    "slug": "matriz",
                    "embed": "matris",
                    "ativo": "1",
                    "validade": "20231231",
                    "dataCadastro": "20230907"
                }
            ]
        }
    """

    val gson = Gson()
    val data = gson.fromJson(json, ModuloResponse::class.java)

    ModuleScreen(
        navController = navController,
        modulesState = remember { mutableStateOf(data.modulos) },
        onModuleSelected = { moduleName ->
            // Lide com a seleção do módulo aqui
            if (moduleName == "Eventos") {
                navController.navigate("eventScreen")
            } else {
                // Lógica para lidar com outros módulos
            }
        }
    )
}

