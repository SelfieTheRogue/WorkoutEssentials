package no.hiof.workoutessentials.ui.planner

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.workoutessentials.model.Exercise
import no.hiof.workoutessentials.service.api.ApiViewModel

var exerciseList: List<Exercise>? = null

@Composable
fun Planner() {

    val viewModel: ApiViewModel = viewModel()

    var showEditor by remember { mutableStateOf(false)}
    var buttonDay by remember { mutableStateOf("")}

    val data by viewModel.data.observeAsState()

    DisposableEffect(Unit) {
        viewModel.fetchData("exercises?")

        onDispose { }
    }

    data?.let {
        exerciseList = it
    }

    if (showEditor == true) {

        Dialog(onDismissRequest = {showEditor = false}) {

            Card (modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
                shape = RoundedCornerShape(8.dp)
            ){
            }
            Column() {
                Button(onClick = { showEditor = false }) {

                }
                LazyColumn() {
                    exerciseList?.let {
                        items(it.size) { index ->
                            Button(onClick = { /*TODO*/ }) {
                                Text(text = exerciseList!!.get(index).name)
                            }
                        }
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "The Planner",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)
        Column (modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally){
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Mon",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { showEditor = true
                                     buttonDay = "monday"},
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Tue",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Wed",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Thu",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Fri",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sat",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
                        Text("Edit")
                    }
                    Text(text = "No Exercises added",
                        modifier = Modifier.weight(1F))
                }
            }
            Row (modifier = Modifier
                .weight(1.0F)
                .padding(0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Sun",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F))
                Column (modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Button(onClick = { /*TODO*/ },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(10.dp, 0.dp)) {
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