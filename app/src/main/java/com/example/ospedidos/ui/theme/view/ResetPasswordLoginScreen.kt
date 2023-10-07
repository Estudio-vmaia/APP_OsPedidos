import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ospedidos.R
import com.google.i18n.phonenumbers.PhoneNumberUtil

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordLoginScreen(
    navController: NavController,
    phoneNumber: String,
    onPhoneNumberChange: (String) -> Unit,
    onHelpClick: () -> Unit,
    onSendSMSClick: () -> Unit
) {
    val blueColor = colorResource(id = R.color.blue_pedidos)
    val phoneNumberUtil = PhoneNumberUtil.getInstance()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val formattedPhoneNumber = formatPhoneNumber(phoneNumber, phoneNumberUtil)

            TextField(
                value = formattedPhoneNumber,
                onValueChange = { newValue ->
                    val numericPhoneNumber = newValue.filter { it.isDigit() }
                    onPhoneNumberChange(numericPhoneNumber)
                },
                label = { Text(text = "Número de Celular") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = null
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )


            Button(
                onClick = {
                    onSendSMSClick()

                    // navController.navigate("sendTokenScreen/$phoneNumber")
                  //  callApiLogin(navController, phoneNumber)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Enviar SMS")
            }
            Button(
                onClick = { onHelpClick() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(text = "Precisa de Ajuda?")
            }
        }
    }
}

private fun formatPhoneNumber(phoneNumber: String, phoneNumberUtil: PhoneNumberUtil): String {
    return try {
        val phoneNumberProto = phoneNumberUtil.parse(phoneNumber, null)
        phoneNumberUtil.format(phoneNumberProto, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL)
    } catch (e: Exception) {
        phoneNumber
    }
}

/*
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun LoginScreenPreview() {

}*/
