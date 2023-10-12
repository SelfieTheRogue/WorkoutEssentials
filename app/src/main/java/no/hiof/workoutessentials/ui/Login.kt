package no.hiof.workoutessentials.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import no.hiof.workoutessentials.R

@Composable
fun Login(login: () -> Unit) {

    var password by remember { mutableStateOf("")}

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "This is the Login Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        OutlinedTextField(value = "Username", onValueChange = {})
        OutlinedTextField(value = password, onValueChange = { password = it},
            visualTransformation = PasswordVisualTransformation(), keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,
                )
        )

        Button(onClick = { }) {
            Text(text = stringResource(R.string.login))
        }
    }
}

@Preview
@Composable
fun LoginPreview(){
    Login(login = {})
}