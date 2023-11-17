package no.hiof.workoutessentials.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.hiof.workoutessentials.service.api.ApiViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Home(viewModel: ApiViewModel) {

    val data by viewModel.data.observeAsState()
    DisposableEffect(Unit) {
        viewModel.fetchData()

        onDispose { }
    }

    val date = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("EEEE")
    val weekDates = listOf<LocalDate>(
        date,
        date.plusDays(1),
        date.plusDays(2),
        date.plusDays(3),
        date.plusDays(4),
        date.plusDays(5),
        date.plusDays(6)
    )

    var exercise by remember { mutableStateOf(date.format(formatter)) }

    Row (modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp, 3.dp)
                    .weight(1F)){


                for (date in weekDates) {
                    Button(onClick = {exercise = date.format(formatter)}, modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth(), shape = RectangleShape) {
                        Text(date.format(formatter), modifier = Modifier.fillMaxWidth())
                    }
                }

            }
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)) {
            Text(exercise,
                style = MaterialTheme.typography.headlineLarge)
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                /*data?.let {
                    for (exercises in it) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = exercises.name)
                        }
                    }
                }*/
            }
        }
    }
}
@Preview
@Composable
fun HomePreview() {
    Home(ApiViewModel())
}