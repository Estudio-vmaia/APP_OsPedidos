package com.example.ospedidos.presentation.presenter

import android.content.Context
import android.util.Log
import androidx.compose.runtime.*
import androidx.navigation.NavController
import com.example.ospedidos.callCategory
import com.example.ospedidos.callEvent
import com.example.ospedidos.callModules
import com.example.ospedidos.callProduct
import com.example.ospedidos.presentation.model.login.Authenticator
import com.example.ospedidos.presentation.model.modules.Modulo
import com.example.ospedidos.service.api.Api
import com.example.ospedidos.utils.SharedPreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginPresenter(private val context: Context) {
    var username by mutableStateOf("")
    var password by mutableStateOf("")
    var phoneNumber by mutableStateOf("")

    fun performLogin(
        navController: NavController,
        onComplete: (List<Modulo>?) -> Unit
    ) {
        val numericPhoneNumber = username.filter { it.isDigit() }
        val service = Api().service

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
                    var idEvent: String
                    var idCategory: String

                    SharedPreferenceManager.saveLoginData(context, slug, embed, user)

                    callModules(slug, embed, user, navController) { listaDeModulos ->
                        if (listaDeModulos != null) {
                            onComplete(listaDeModulos)
                            SharedPreferenceManager.saveModuleList(context, listaDeModulos)
                            navController.navigate("moduleScreen") {
                                popUpTo("loginScreen") { inclusive = true }
                            }
                            if (slug != null && embed != null && user != null) {
                                callEvent(context, slug, embed, user, navController) { listaDeEventos ->
                                    if (listaDeEventos != null) {
                                        SharedPreferenceManager.saveEventList(context, listaDeEventos)
                                        idEvent = listaDeEventos[0].id
                                        SharedPreferenceManager.saveIdEvent(context, idEvent)
                                        if (slug != null && embed != null && user != null && idEvent != null) {

                                            callCategory(
                                                context,
                                                slug,
                                                embed,
                                                user,
                                                idEvent,
                                                navController
                                            ) { listaDeCategorias ->
                                                if (listaDeCategorias != null) {
                                                    SharedPreferenceManager.saveCategoryList(
                                                        context,
                                                        listaDeCategorias
                                                    )
                                                    val idCategory = listaDeCategorias[0].id_categoria
                                                    SharedPreferenceManager.saveIdCategory(
                                                        context,
                                                        idCategory
                                                    )
                                                    if (slug != null && embed != null && user != null && idEvent != null && idCategory != null) {

                                                        callProduct(
                                                            context,
                                                            slug,
                                                            embed,
                                                            user,
                                                            idEvent,
                                                            idCategory,
                                                            navController
                                                        ) { listaDeProdutos ->
                                                            if (listaDeProdutos != null) {
                                                                SharedPreferenceManager.saveProductList(
                                                                    context,
                                                                    listaDeProdutos
                                                                )
                                                                val idProduct = listaDeProdutos[0].id
                                                                SharedPreferenceManager.saveIdProduct(
                                                                    context,
                                                                    idProduct
                                                                )
                                                            }
                                                        }

                                                    }

                                                }
                                            }

                                        }
                                    }
                                }
                            } else {
                                Log.d("ResponseLogin", "ResponseApi ERROR -> " + response.message())
                            }
                        } else {
                            Log.d("ResponseLogin", "ResponseApi ERROR -> " + response.message())
                        }
                    }

                } else {
                    Log.d("ResponseLogin", "ResponseApi ERROR -> " + response.message())
                }
            }

            override fun onFailure(call: Call<Authenticator?>, t: Throwable) {
                Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
            }
        })
    }

}

