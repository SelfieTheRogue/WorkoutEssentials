package no.hiof.workoutessentials.ui.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun Home() {

    var exercise by remember { mutableStateOf("exercise") }

    Row (modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp, 3.dp)
                    .weight(1F)){
                var date = LocalDate.now()
                val formatter = DateTimeFormatter.ofPattern("EEEE")
                var weekDates = listOf<LocalDate>(
                    date,
                    date.plusDays(1),
                    date.plusDays(2),
                    date.plusDays(3),
                    date.plusDays(4),
                    date.plusDays(5),
                    date.plusDays(6)
                )

                for (date in weekDates) {
                    Button(onClick = {exercise = date.format(formatter)}, modifier = Modifier
                        .weight(1F)
                        .fillMaxWidth(), shape = RectangleShape) {
                        Text(date.format(formatter), modifier = Modifier.fillMaxWidth())
                    }
                }

            }
        }
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)) {
            Text(exercise,
                style = MaterialTheme.typography.headlineLarge)
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                Row (verticalAlignment = Alignment.CenterVertically){
                    Text(text = exercise,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .border(Dp.Hairline, Color.Black)
                            .padding(5.dp, 5.dp)
                            .weight(0.3F))
                    Column (modifier = Modifier.weight(0.7F)){
                        Text(text = "navn",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center)
                        Text(text = "beskrivelse",
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center)
                    }
                    Text(text = "Checkbox")
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    Home()
}