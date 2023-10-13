package no.hiof.workoutessentials.ui

import android.widget.CalendarView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun Planner() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "This is the Planner Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)
        Text(text = "Will contain the functionality to setup training weeks",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center)
        AndroidView(
            { CalendarView(it) },
            modifier = Modifier.wrapContentWidth()
        )
    }
}
@Preview
@Composable
fun PlannerPreview(){
    Planner()
}