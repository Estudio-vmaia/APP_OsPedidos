package com.example.ospedidos.ui.theme.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ospedidos.ui.theme.OsPedidosTheme
import org.json.JSONObject

class ModuleScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val json = """{
                "arrayName": "modulos",
                "arrayColunas": "id,idModulo,idCliente,cliente,slug,embed,ativo,validade,dataCadastro",
                "arrayKeys": 2,
                "modulos": {
                    "modulos1": {
                        "nomeModulo": "Eventos",
                        "urlModulo": "eventos",
                        "id": "1",
                        "idModulo": "1",
                        "idCliente": "11",
                        "cliente": "Matriz",
                        "slug": "matriz",
                        "embed": "matris",
                        "ativo": "1",
                        "validade": "20231231",
                        "dataCadastro": "20230907"
                    },
                    "modulos2": {
                        "nomeModulo": "Loja",
                        "urlModulo": "loja",
                        "id": "2",
                        "idModulo": "2",
                        "idCliente": "11",
                        "cliente": "Matriz",
                        "slug": "matriz",
                        "embed": "matris",
                        "ativo": "1",
                        "validade": "20231231",
                        "dataCadastro": "20230907"
                    }
                }
            }"""

            val moduleData = parseModuleData(json)

            OsPedidosTheme {
                ModuleScreenContent(moduleData)
            }
        }
    }

    data class ModuleData(val moduleName: String, val moduleUrl: String)

    private fun parseModuleData(jsonString: String): List<ModuleData> {
        val jsonObject = JSONObject(jsonString)
        val moduleList = mutableListOf<ModuleData>()
        val modulosObject = jsonObject.getJSONObject("modulos")

        modulosObject.keys().forEach { key ->
            val moduleObject = modulosObject.getJSONObject(key)
            val moduleName = moduleObject.getString("nomeModulo")
            val moduleUrl = moduleObject.getString("urlModulo")
            moduleList.add(ModuleData(moduleName, moduleUrl))
        }

        return moduleList
    }

    @Composable
    fun ModuleScreenContent(moduleData: List<ModuleData>) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            moduleData.forEach { data ->
                Button(
                    onClick = {

                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(text = data.moduleName)
                }
            }
        }
    }
}
