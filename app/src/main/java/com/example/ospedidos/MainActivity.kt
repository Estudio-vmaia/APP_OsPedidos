package com.example.ospedidos

import LoginScreen
import ResetPasswordLoginScreen
import SendTokenScreen
import android.content.Context
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ospedidos.presentation.model.category.Category
import com.example.ospedidos.presentation.model.category.CategoryResponse
import com.example.ospedidos.presentation.model.event.Event
import com.example.ospedidos.presentation.model.event.EventResponse
import com.example.ospedidos.presentation.model.login.Authenticator
import com.example.ospedidos.presentation.model.modules.Modulo
import com.example.ospedidos.presentation.model.modules.ModuloResponse
import com.example.ospedidos.presentation.model.product.Product
import com.example.ospedidos.presentation.model.product.ProductResponse
import com.example.ospedidos.presentation.presenter.LoginPresenter
import com.example.ospedidos.presentation.view.CategoryScreen
import com.example.ospedidos.presentation.view.GenericErrorScreen
import com.example.ospedidos.presentation.view.ModuleScreen
import com.example.ospedidos.presentation.view.OrderScreen
import com.example.ospedidos.service.api.Api
import com.example.ospedidos.service.api.RetrofitInterface
import com.example.ospedidos.ui.theme.OsPedidosTheme
import com.example.ospedidos.ui.theme.view.EventScreen
import com.example.ospedidos.utils.SharedPreferenceManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {

    lateinit var appContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OsPedidosTheme {
                val navController = rememberNavController()
                MainContent(navController)
            }
        }
        appContext = this

    }

    @Composable
    fun MainContent(navController: NavHostController) {
        val modulesState = remember { mutableStateOf<List<Modulo>>(emptyList()) }
        val loginPresenter = LoginPresenter(appContext)
        var username by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }


        NavHost(navController, startDestination = "loginScreen") {
            composable("loginScreen") {
                LoginScreen(
                    username = loginPresenter.username,
                    password = loginPresenter.password,
                    onUsernameChange = { newUsername -> loginPresenter.username = newUsername },
                    onPasswordChange = { newPassword -> loginPresenter.password = newPassword },
                    onLoginClick = {
                        loginPresenter.performLogin(navController) { listaDeModulos ->
                            if (listaDeModulos != null) {
                                modulesState.value = listaDeModulos // Atualize o estado dos módulos
                                navController.navigate("moduleScreen") {
                                    popUpTo("loginScreen") { inclusive = true }
                                }
                            } else {
                                // Lidar com o erro
                            }
                        }
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
                val moduleList = SharedPreferenceManager.getModuleList(this@MainActivity)
                ModuleScreen(
                    navController = navController,
                    modulesState = remember { mutableStateOf(moduleList) },
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
            composable("eventScreen") {
                val savedEventResponse = SharedPreferenceManager.getEventResponseApi(appContext)
                EventScreen(
                    navController = navController,
                    onEventSelected = { evento, slug, embed, user ->
                        callCategory(appContext, slug, embed, user, evento.id, navController) { listaDeCategorias ->
                            if (listaDeCategorias != null) {
                                navController.navigate("categoryScreen")
                            } else {
                                navController.navigate("genericErrorScreen")
                            }
                        }
                    }
                )
            }

            composable("categoryScreen") {
                val eventId = SharedPreferenceManager.getIdEvent(appContext)
                val selectedEvent: Event? = getEventById(appContext, eventId)

                CategoryScreen(
                    navController = navController,
                    selectedEvent = selectedEvent,
                    onCategorySelected = {slug, embed, user, idEvent, idCategoria ->
                        callProduct(appContext, slug, embed, user, idEvent, idCategoria) { listaDeProdutos ->
                            if (listaDeProdutos != null) {
                                navController.navigate("orderScreen")
                            } else {
                                navController.navigate("genericErrorScreen")
                            }
                        }
                    }
                )
            }
            composable("orderScreen") {
                OrderScreen(
                    navController = navController,
                    onProductClick = { item, id, preco ->
                        // Armazene os dados nos SharedPreferences aqui
                        val productData = "Item: $item, ID: $id, Preço: $preco"
                        SharedPreferenceManager.saveProductData(appContext, productData)

                        // Adicional: você pode querer navegar para uma tela diferente ou fazer outra coisa aqui
                    }
                )
            }
            composable("genericErrorScreen") {
                GenericErrorScreen(
                    navController = navController
                )
            }
        }
    }
}
fun getEventById(context: Context, eventId: String?): Event? {
    if (eventId != null) {
        val eventList = SharedPreferenceManager.getEventList(context)
        return eventList.find { it.id == eventId }
    }
    return null
}

/*fun initValuesModules(slug: String?, embed: String?, user: String?){ //replace or persist with shared preferences
    Log.d("Slug:", "-> $slug")
    Log.d("Embed:", "-> $embed")
    Log.d("User:", "-> $user")
}*/
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

fun callModules(
    slug: String?,
    embed: String?,
    username: String?,
    navController: NavController,
    onComplete: (List<Modulo>?) -> Unit
) {
    val service: RetrofitInterface = Api().service
    val call: Call<ModuloResponse?>? = service.modules(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        slug,
        embed,
        username
    )

    call?.enqueue(object : Callback<ModuloResponse?> {
        override fun onResponse(call: Call<ModuloResponse?>, response: Response<ModuloResponse?>) {
            if (response.isSuccessful) {
                val moduloResponse: ModuloResponse? = response.body()
                if (moduloResponse != null) {
                    val moduloList: List<Modulo> = moduloResponse.modulos
                    Log.d("Response", "ResponseApi ok -> " + moduloList.toString())
                    navController.navigate("moduleScreen")
                    onComplete(moduloList) // Chame o callback com a lista de módulos
                } else {
                    Log.d("Response", "ResponseApi ok, mas body vazio")
                    onComplete(null)
                }
            } else {
                Log.d("Response", "ResponseApi ERROR -> " + response.message())
                onComplete(null)
            }
        }

        override fun onFailure(call: Call<ModuloResponse?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
            onComplete(null)
        }
    })
}

fun callEvent(
    context: Context,
    slug: String?,
    embed: String?,
    username: String?,
    navController: NavController,
    onComplete: (List<Event>?) -> Unit
) {
    val service: RetrofitInterface = Api().service
    val call: Call<EventResponse>? = service.event(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        slug,
        embed,
        username
    )

    call?.enqueue(object : Callback<EventResponse?> {
        override fun onResponse(call: Call<EventResponse?>, response: Response<EventResponse?>) {
            if (response.isSuccessful) {
                val eventResponse = response.body()
                if (eventResponse != null) {
                    val eventList: List<Event> = eventResponse.eventos
                    Log.d("callEvent", "Event list: $eventList")
                    onComplete(eventList)
                    SharedPreferenceManager.saveEventList(context, eventList)
                    for (evento in eventList) {
                        SharedPreferenceManager.saveSelectedEventId(context, evento.id, evento.nome)
                    }
                    val arrayName = eventResponse.arrayName
                    val arrayColunas = eventResponse.arrayColunas
                    val arrayKeys = eventResponse.arrayKeys
                    SharedPreferenceManager.saveEventResponseApi(
                        context,
                        arrayName,
                        arrayColunas,
                        arrayKeys
                    )

                } else {
                    Log.d("callEvent", "Event response is null")
                    onComplete(null)
                }
            } else {
                Log.d("callEvent", "ResponseApi ERROR -> " + response.message())
                onComplete(null)
            }
        }

        override fun onFailure(call: Call<EventResponse?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
            onComplete(null)
        }
    })
}

fun callCategory(
    context: Context,
    slug: String?,
    embed: String?,
    username: String?,
    idEvent: String?,
    navController: NavController,
    onComplete: (List<Category>?) -> Unit
) {

    val service: RetrofitInterface = Api().service
    val call: Call<CategoryResponse>? = service.category(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        slug,
        embed,
        username,
        idEvent
    )

    call?.enqueue(object : Callback<CategoryResponse?> {
        override fun onResponse(
            call: Call<CategoryResponse?>,
            response: Response<CategoryResponse?>
        ) {
            if (response.isSuccessful) {
                val categoryResponse = response.body()
                if (categoryResponse != null) {
                    val categoryList: List<Category> = categoryResponse.categorias
                    Log.d("callCategory", "Category list: $categoryList")
                    onComplete(categoryList)
                    SharedPreferenceManager.saveCategoryList(context, categoryList)

                } else {
                    Log.d("callCategory", "Event response is null")
                    onComplete(null)
                }
            } else {
                Log.d("callCategory", "ResponseApi ERROR -> " + response.message())
                onComplete(null)
            }
        }

        override fun onFailure(call: Call<CategoryResponse?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
            onComplete(null)
        }
    })
}

fun callProduct(
    context: Context,
    slug: String?,
    embed: String?,
    username: String?,
    idEvent: String?,
    idCategory: String?,
    onComplete: (List<Product>?) -> Unit
) {
    val service: RetrofitInterface = Api().service
    val call: Call<ProductResponse>? = service.products(
        "application/json",
        "Basic dXNlcmFwaTphSzM4N0tSZ3hPU202bUV2YXlGVTIxeENGNlZQUDBu",
        slug,
        embed,
        username,
        idEvent,
        idCategory
    )

    call?.enqueue(object : Callback<ProductResponse?> {
        override fun onResponse(
            call: Call<ProductResponse?>,
            response: Response<ProductResponse?>
        ) {
            if (response.isSuccessful) {
                val productResponse = response.body()
                if (productResponse != null) {
                    val productList: List<Product> = productResponse.produtos
                    Log.d("callProducts", "Products list: $productList")
                    onComplete(productList)
                    SharedPreferenceManager.saveProductList(context, productList)

                } else {
                    Log.d("callProducts", "Product response is null")
                    onComplete(null)
                }
            } else {
                Log.d("callProducts", "ResponseApi ERROR -> " + response.message())
                onComplete(null)
            }
        }

        override fun onFailure(call: Call<ProductResponse?>, t: Throwable) {
            Log.d("Response", "ResponseApi FAILURE -> " + t.printStackTrace().toString())
            onComplete(null)
        }
    })
}





