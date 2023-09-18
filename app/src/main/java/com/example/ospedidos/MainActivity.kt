package com.example.ospedidos

import LoginScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent() {
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginScreen(
            username = username,
            password = password,
            onUsernameChange = { newUsername -> username = newUsername },
            onPasswordChange = { newPassword -> password = newPassword },
            onLoginClick = { callApiLogin() }
        )
    }

    @Composable
    fun MainContent(
        username: String,
        password: String,
        onUsernameChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit
    ) {
        LoginScreen(
            username = username,
            password = password,
            onUsernameChange = onUsernameChange,
            onPasswordChange = onPasswordChange,
            onLoginClick = { callApiLogin()}
        )
    }

    fun callApiLogin() {
        val service: RetrofitInterface = Api().service
        val call: Call<Autenticacao?>? = service.osPedidosLogin(
            "application/json",
            "Basic dXNlcmFwaTpLMzg3S1JneE9TbTZtRXZheUZVMjF4Q0Y2VlBQMG4=",
            "renan.maia",
            "qa"
        )

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
}
