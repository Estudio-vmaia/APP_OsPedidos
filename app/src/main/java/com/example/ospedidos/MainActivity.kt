package com.example.ospedidos

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ospedidos.model.Autenticacao
import com.example.ospedidos.model.Login
import com.example.ospedidos.service.api.Api
import com.example.ospedidos.service.api.RetrofitInterface
import com.example.ospedidos.ui.theme.OsPedidosTheme
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OsPedidosTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
        callApiLogin()
        callApiLoginTeste()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OsPedidosTheme {
        Greeting("Android")
    }
}

fun callApiLogin() {
    val service: RetrofitInterface = Api().service
    val call: Call<Autenticacao?>? = service.osPedidosLogin("application/json", "Basic dXNlcmFwaTpLMzg3S1JneE9TbTZtRXZheUZVMjF4Q0Y2VlBQMG4=","renan.maia", "qa")

    call?.enqueue(object : Callback<Autenticacao?> {
        override fun onResponse(
            call: Call<Autenticacao?>,
            response: Response<Autenticacao?>
        ) {
            if (response.isSuccessful) {
                val authentication: Autenticacao? = response.body()
                Log.d("Response", "Response ok -> " + authentication.toString())

            }
        }

        override fun onFailure(call: Call<Autenticacao?>, t: Throwable) {
            Log.d("Response", "Response FAILURE -> " + t.printStackTrace().toString())
        }
    })
}
fun callApiLoginTeste() {
   /* val client = OkHttpClient()
    var mediaType = "application/json"
    val body = "{\r\n    \"type\": \"CUPOM\",\r\n    \"format\": \"png\",\r\n    \"content\": \"aaa\"\r\n}".toRequestBody(mediaType)
    val request = Request.Builder()
        .url("https://ospedidossandbox.com.br/API/login.php?usuario=renan.maia&senha=qa")
        .post(body)
        .addHeader("Content-Type", "application/json")
        .addHeader("Authorization", "Basic dXNlcmFwaTpLMzg3S1JneE9TbTZtRXZheUZVMjF4Q0Y2VlBQMG4=")
        .build()
    val response = client.newCall(request).execute()
    Log.d("Response", "Response ok -> " + response.body().toString())
*/
}
