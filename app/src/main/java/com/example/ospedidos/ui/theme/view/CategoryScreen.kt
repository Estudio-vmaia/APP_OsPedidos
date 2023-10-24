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
import com.example.ospedidos.utils.SharedPreferenceManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(
    navController: NavController
) {

    val savedCategoryList = SharedPreferenceManager.getCategoryList(LocalContext.current)


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

        // Crie um item para cada evento na lista
        itemsIndexed(savedCategoryList) { index, categoria ->
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (categoria.nome == "BEBIDAS") {
                        navController.navigate("orderScreen") {
                            popUpTo("eventScreen") { inclusive = true }
                        }                    } else {
                        // Lógica para lidar com outros eventos
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = categoria.nome)
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
fun CategoryScreenPreview() {
    val navController = rememberNavController()
    CategoryScreen(navController = navController)

}
