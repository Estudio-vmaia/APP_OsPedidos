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
    onEventSelected: (Event, String, String, String) -> Unit
) {

    val context = LocalContext.current
    val savedEventList = SharedPreferenceManager.getEventList(context)
    val slug = SharedPreferenceManager.getSlug(context)!!
    val embed = SharedPreferenceManager.getEmbed(context)!!
    val user = SharedPreferenceManager.getUser(context)!!


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
                    onEventSelected(evento, slug, embed, user)
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

