import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import android.os.CountDownTimer
import com.example.ospedidos.callResetPasswordLogin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendTokenScreen(
    navController: NavController,
    phoneNumber: String,
    onCodeSent: () -> Unit
) {
    var verificationCode by remember { mutableStateOf("") }
    var isCountingDown by remember { mutableStateOf(false) }
    var remainingTime by remember { mutableStateOf(240L) } // 4 minutos em segundos

    val timer = remember { // O timer de contagem regressiva
        object : CountDownTimer(remainingTime * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                remainingTime = millisUntilFinished / 1000
            }

            override fun onFinish() {
                isCountingDown = false
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Digite o código enviado para o número $phoneNumber",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = verificationCode,
            onValueChange = { newValue -> verificationCode = newValue },
            label = { Text("Código SMS") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                callResetPasswordLogin(phoneNumber, navController)
                if (!isCountingDown) {
                    isCountingDown = true
                    timer.start()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isCountingDown
        ) {
            Text("Enviar Código")
        }

        if (isCountingDown) {
            Text(
                text = "Tempo restante: ${remainingTime / 60}:${String.format("%02d", remainingTime % 60)}",
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SendTokenScreenPreview() {
}
