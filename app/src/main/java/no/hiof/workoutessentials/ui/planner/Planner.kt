package no.hiof.workoutessentials.ui.planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Planner() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "The Planner",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)
        Column (modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Mon",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Tue",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Wed",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Thu",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Fri",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sat",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier.weight(1.0F).padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sun",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier.weight(1.0F).padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
        }
    }
}
@Preview
@Composable
fun PlannerPreview(){
    Planner()
}