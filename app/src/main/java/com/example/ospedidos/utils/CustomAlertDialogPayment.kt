package com.example.ospedidos.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun CustomAlertDialogPayment(
    onPaymentMethodSelected: (String) -> Unit,
    onCloseClicked: () -> Unit
) {
    var selectedPaymentMethod by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onCloseClicked,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(32.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Selecione a forma de pagamento")

                    // Use LazyColumn para exibir os botões em uma lista
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        val paymentMethods = listOf("Débito", "Pix", "Dinheiro")

                        items(paymentMethods) { paymentMethod ->
                            Button(
                                onClick = {
                                    selectedPaymentMethod = paymentMethod
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                Text(paymentMethod)
                            }
                        }
                    }

                    // Adicione um botão para confirmar a forma de pagamento
                    Button(
                        onClick = {
                            onPaymentMethodSelected(selectedPaymentMethod)
                            onCloseClicked()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .padding(4.dp)
                            .align(Alignment.End)
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    )
}


