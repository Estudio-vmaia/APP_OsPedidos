package com.example.ospedidos

import EventScreen
import LoginScreen
import ResetPasswordLoginScreen
import SendTokenScreen
import StoreCategoryScreen
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ospedidos.model.login.Authenticator
import com.example.ospedidos.service.api.Api
import com.example.ospedidos.service.api.RetrofitInterface
import com.example.ospedidos.ui.theme.OsPedidosTheme
import com.example.ospedidos.ui.theme.view.ModuleScreen
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
        var phoneNumber by remember { mutableStateOf("") }
        val navController = rememberNavController()

        NavHost(navController, startDestination = "loginScreen") {
            composable("loginScreen") {
                LoginScreen(
                    username = username,
                    password = password,
                    onUsernameChange = { newUsername -> username = newUsername },
                    onPasswordChange = { newPassword -> password = newPassword },
                    onLoginClick = {
                        callLogin(username, password, navController)
                    },
                    onForgotPasswordClick = {
                        navController.navigate("resetPasswordLoginScreen")
                    }
                )
            }
            composable("resetPasswordLoginScreen") {
                ResetPasswordLoginScreen(
                    navController = navController,
                    phoneNumber = phoneNumber,
                    onPhoneNumberChange = { newPhoneNumber -> phoneNumber = newPhoneNumber },
                    onHelpClick = { },
                    onSendSMSClick = {
                        callResetPasswordLogin(phoneNumber, navController)
                    }
                )
            }
            composable("sendTokenScreen/{phoneNumber}") {
                val phoneNumber = it.arguments?.getString("phoneNumber") ?: ""
                SendTokenScreen(
                    navController = navController,
                    phoneNumber = phoneNumber,
                    onCodeSent = {
                        callResetPasswordLogin(phoneNumber, navController)
                    }
                )
            }
            composable("moduleScreen") {
                  ModuleScreen(
                      navController = navController
                  )
            }
            composable("eventScreen") {
                EventScreen(
                    navController = navController
                )
            }
            composable("storeCategoryScreen") {
                StoreCategoryScreen(
                    navController = navController
                )
            }
        }
    }
}

/*fun initValuesModules(slug: String?, embed: String?, user: String?){ //replace or persist with shared preferences
    Log.d("Slug:", "-> $slug")
    Log.d("Embed:", "-> $embed")
    Log.d("User:", "-> $user")
}*/
fun callLogin(username: String, password: String, navController: NavController) {
    val numericPhoneNumber = username.filter { it.isDigit() }

    val service: RetrofitInterface = Api().service
    val call: Call<Authenticator?>? = service.loginApi(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        numericPhoneNumber,
        password

    )

    call?.enqueue(object : Callback<Authenticator?> {
        override fun onResponse(
            call: Call<Authenticator?>,
            response: Response<Authenticator?>
        ) {
            val authentication: Authenticator? = response.body()
            if (response.isSuccessful) {
                Log.d("Response", "ResponseApi ok -> " + authentication.toString())
                var slug = authentication?.login?.slug
                var embed = authentication?.login?.embed
                var user = authentication?.login?.usuario

                callModules(slug, embed, user, navController)
            } else {
                Log.d("Response", "ResponseApi ERROR -> " + response.message())

            }
        }

        override fun onFailure(call: Call<Authenticator?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
        }
    })
}

fun callResetPasswordLogin(phoneNumber: String, navController: NavController) {
    val numericPhoneNumber = phoneNumber.filter { it.isDigit() }

    val service: RetrofitInterface = Api().service
    val call: Call<Authenticator?>? = service.resetPasswordLogin(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        numericPhoneNumber,
        "qa"
    )

    call?.enqueue(object : Callback<Authenticator?> {
        override fun onResponse(
            call: Call<Authenticator?>,
            response: Response<Authenticator?>
        ) {
            val authentication: Authenticator? = response.body()
            if (response.isSuccessful) {
                Log.d("Response", "ResponseApi ok -> " + authentication.toString())

                navController.navigate("sendTokenScreen/$numericPhoneNumber")
            } else {
                Log.d("Response", "ResponseApi ERROR -> " + response.message())

            }
        }

        override fun onFailure(call: Call<Authenticator?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
        }
    })
}

fun callModules(slug: String?, embed: String?, username: String?, navController: NavController) {

    val service: RetrofitInterface = Api().service
    val call: Call<Authenticator?>? = service.modules(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        slug,
        embed,
        username
    )

    call?.enqueue(object : Callback<Authenticator?> {
        override fun onResponse(
            call: Call<Authenticator?>,
            response: Response<Authenticator?>
        ) {
            val authentication: Authenticator? = response.body()
            if (response.isSuccessful) {
                Log.d("Response", "ResponseApi ok -> " + authentication.toString())
                navController.navigate("moduleScreen")
            } else {
                Log.d("Response", "ResponseApi ERROR -> " + response.message())

            }
        }

        override fun onFailure(call: Call<Authenticator?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
        }
    })
}




