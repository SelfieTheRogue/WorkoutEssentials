package no.hiof.workoutessentials.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Planner() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "This is the Planner Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)
        Text(text = "Will contain the functionality to setup training weeks",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center)
    }

}