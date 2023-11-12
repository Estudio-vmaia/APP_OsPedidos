package com.example.ospedidos.ui.theme.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ospedidos.presentation.model.event.Event
import com.example.ospedidos.presentation.model.event.EventResponse
import com.example.ospedidos.utils.SharedPreferenceManager
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventScreen(
    navController: NavController,
    onEventSelected: (Event) -> Unit
) {

    val savedEventList = SharedPreferenceManager.getEventList(LocalContext.current)


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Text(
                text = "Os Pedidos - EVENTOS",
                modifier = Modifier
                    .padding(vertical = 16.dp),
            )
        }

        // Crie um item para cada evento na lista
        itemsIndexed(savedEventList) { index, evento ->
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onEventSelected(evento)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = evento.nome)
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun EventScreenPreview() {
    val navController = rememberNavController()

    val json = """
        {
    "arrayName": "eventos",
    "arrayColunas": "nome,id,dataini,horaini,datafim,horafim,acumulativo,tipocob,valorminimo,status",
    "arrayKeys": 2,
    "eventos": [
        {
            "nome": "Quermesse 2023",
            "id": "60",
            "dataini": "20230529",
            "horaini": "1800",
            "datafim": "20240730",
            "horafim": "2200",
            "acumulativo": "N",
            "tipocob": "debit,credit,pix",
            "valorminimo": null,
            "status": "A"
        },
        {
            "nome": "Quermesse 2027",
            "id": "61",
            "dataini": "20230529",
            "horaini": "1800",
            "datafim": "20240730",
            "horafim": "2200",
            "acumulativo": "N",
            "tipocob": "debit,pix",
            "valorminimo": null,
            "status": "A"
        }
    ]
}
    """

    val gson = Gson()
    val data = gson.fromJson(json, EventResponse::class.java)

    EventScreen(
        navController = navController,
        onEventSelected = { eventName ->
            // Lide com a seleção do módulo aqui
            if (eventName.nome == "Eventos") {
                navController.navigate("eventScreen")
            } else {
                // Lógica para lidar com outros módulos
            }
        }
    )

}

