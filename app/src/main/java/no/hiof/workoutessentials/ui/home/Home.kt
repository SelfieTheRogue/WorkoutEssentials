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
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.workoutessentials.service.StorageService
import no.hiof.workoutessentials.service.api.ApiViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale


@Composable
fun Home(storageService: StorageService) {

    val viewModel: ApiViewModel = viewModel()

    var buttonClicked by remember { mutableStateOf(false) }

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

    fun urlGen (exercises: List<String>): String {
        var url = ""
        if (exercises != null) {
            url = "exercises?"
            for (exercise in exercises) {
                url = url + "name=" + exercise.replace("_", "+") + "&"
            }
            url = url.dropLast(1)
        }

        return url
    }

    val exercisesState = remember { mutableStateOf<List<String>>(emptyList()) }

    var selectedDay by remember { mutableStateOf(date.format(formatter)) }

    LaunchedEffect(buttonClicked, selectedDay) {
        if (buttonClicked) {
            try {
                // Perform the asynchronous operation
                val exerciseList = storageService.getExercises(selectedDay.replaceFirstChar {
                    it.lowercase(
                        Locale.ROOT
                    )
                })

                // Update the UI state with the result
                exercisesState.value = exerciseList

            } catch (e: Exception) {
                // Handle errors
            } finally {
                // Reset the buttonClicked state after the operation is complete
                buttonClicked = false
            }
        }
    }

    val data by viewModel.data.observeAsState()
    DisposableEffect(Unit) {
        viewModel.fetchData("exercises?name=Landmine+twist")

        onDispose { }
    }



    Row (modifier = Modifier.fillMaxSize()){
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(.3F)){
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(0.dp, 3.dp)
                    .weight(1F)){


                for (date in weekDates) {
                    Button(onClick = {selectedDay = date.format(formatter)
                        buttonClicked = true
                                     },
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(), shape = RectangleShape) {
                        Text(date.format(formatter), modifier = Modifier.fillMaxWidth())
                    }
                }

            }
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)) {
            Text(selectedDay,
                style = MaterialTheme.typography.headlineLarge)
            Column (horizontalAlignment = Alignment.CenterHorizontally){
                for (exercise in exercisesState.value) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = exercise.replace("_", " "))
                    }
                }
                data?.let {
                    for (exercise in it) {
                        if(exercise.name == "Landmine twist"){
                            Text(text = exercise.name)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HomePreview() {
    lateinit var storageService: StorageService
    Home(storageService)
}