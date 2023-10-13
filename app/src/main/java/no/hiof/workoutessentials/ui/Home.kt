package no.hiof.workoutessentials.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.text.SimpleDateFormat
import java.util.Date

@Composable
fun Home() {
    Row (modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally){
            Column (modifier = Modifier.padding(3.dp, 3.dp)){
                val sdf = SimpleDateFormat.getDateInstance()
                val ddf = SimpleDateFormat("EEEE")
                val currentDate = sdf.format(Date())
                val currentDay = ddf.format(Date())
                Text(text = currentDay,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center)
                Text(text = currentDate,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center)
            }
        }
        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()){
            Row {
                Text(text = "Bilde",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .border(Dp.Hairline, Color.Black)
                        .padding(5.dp, 5.dp)
                        .weight(0.3F, true))
                Column (modifier = Modifier.weight(0.7F, true)){
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

@Preview
@Composable
fun HomePreview() {
    Home()
}