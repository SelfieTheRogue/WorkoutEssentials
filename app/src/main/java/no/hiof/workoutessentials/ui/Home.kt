package no.hiof.workoutessentials.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Home() {
    Text(text = "This is the Home Screen",
        style = MaterialTheme.typography.headlineLarge,
        textAlign = TextAlign.Center)
}

@Preview
@Composable
fun HomePreview() {
    Home()
}