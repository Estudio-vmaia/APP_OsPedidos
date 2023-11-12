package com.example.ospedidos.presentation.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ospedidos.presentation.model.event.Event
import com.example.ospedidos.utils.SharedPreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController,
    selectedEvent: Event?,
    onCategorySelected: (String, String, String, String, String) -> Unit
) {
    val savedCategoryList = SharedPreferenceManager.getCategoryList(LocalContext.current)
    val context = LocalContext.current
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
                text = "Os Pedidos - CATEGORIAS",
                modifier = Modifier
                    .padding(vertical = 16.dp),
            )
        }

        // Crie um item para cada categoria no evento selecionado
        for (categoria in savedCategoryList) {
            if (categoria.id_evento == selectedEvent?.id) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onCategorySelected(slug, embed, user, selectedEvent.id, categoria.id_categoria)
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = categoria.nome)
                    }
                }
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

