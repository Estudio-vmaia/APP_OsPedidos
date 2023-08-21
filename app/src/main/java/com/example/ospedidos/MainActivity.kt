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
import com.example.ospedidos.service.api.Api
import com.example.ospedidos.service.api.RetrofitInterface
import com.example.ospedidos.ui.theme.OsPedidosTheme
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
    val call: Call<List<Autenticacao?>?>? = service.osPedidosLogin("renan.maia", "qa")

    call?.enqueue(object : Callback<List<Autenticacao?>?> {
        override fun onResponse(
            call: Call<List<Autenticacao?>?>,
            response: Response<List<Autenticacao?>?>
        ) {
            if (response.isSuccessful) {
                val authenticationList: List<Autenticacao?>? = response.body()
                Log.d("Response", "Response ok -> " + authenticationList.toString())
            } else {
                Log.d("Response", "Response error -> " + response.errorBody())
            }
        }

        override fun onFailure(call: Call<List<Autenticacao?>?>, t: Throwable) {
            Log.d("Response", "Response fail -> " + t.stackTrace.toString())
        }
    })
}
