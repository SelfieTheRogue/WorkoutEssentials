package no.hiof.workoutessentials.ui.home

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.workoutessentials.model.Exercise
import no.hiof.workoutessentials.service.StorageService
import no.hiof.workoutessentials.service.api.ApiViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun AddDetailDialog(
    exercise: Exercise,
    onDismiss: () -> Unit,
) {
    Dialog(onDismissRequest = onDismiss)
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(8.dp)
        ) {


            Column() {
                Text(text = "Name: " + exercise.name)
                Text(text = "Muscle Group: " + exercise.muscle)
                Text(text = "Type: " + exercise.type)
                Text(text = "Difficulty: " + exercise.difficulty)
                Text(text = "Equipment: " + exercise.equipment)
                Text(text = "Instructions")
            }
            Divider(thickness = 2.dp, color = Color.Black)
            Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = exercise.instructions, modifier = Modifier
                    .verticalScroll(
                        rememberScrollState()
                    )
                    .weight(5F, false))

                androidx.compose.material3.Button(onClick = onDismiss) {
                    Text(text = "Exit")
                }
            }
        }
    }
}

@Composable
fun Home(storageService: StorageService) {

    val viewModel: ApiViewModel = viewModel()

    var buttonClicked by remember { mutableStateOf(false) }
    var showDetail by remember { mutableStateOf(false) }
    var selectedExercise by remember { mutableStateOf("")}

    var exerciseList by remember { mutableStateOf<List<Exercise>>(emptyList()) }

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
    var selectedDay by remember { mutableStateOf(date.format(formatter)) }

    val exercisesState = remember { mutableStateOf<List<String>>(emptyList()) }

    var orientation = false

    val context = LocalContext.current
    val config = LocalConfiguration.current
    when (config.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            orientation = true
        }
        else -> {
            orientation = false
        }
    }

    LaunchedEffect(Unit) {
        try {
            val exerciseListFromFire = storageService.getExercises(selectedDay.replaceFirstChar {
                it.lowercase(
                    Locale.ROOT
                )
            })
            exercisesState.value = exerciseListFromFire
        } catch (e: Exception) {

        }
    }

    LaunchedEffect(showDetail, selectedExercise) {
        if (showDetail) {
            try {
                viewModel.fetchData("exercises?name=$selectedExercise")
            } catch (e: Exception) {

            }
        }
    }

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

    data?.let {
        exerciseList = it
    }

    if(showDetail) {
        if(selectedExercise != null) {
            for (item in exerciseList) {
                if(item.name == selectedExercise)    {
                    AddDetailDialog(
                        exercise = item,
                        onDismiss = { showDetail = false }
                    )
                }
            }
        }
    }

    Row (modifier = Modifier.fillMaxSize()){
        if (orientation) {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(.3F)){
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(0.dp, 0.dp)
                        .verticalScroll(rememberScrollState())
                        .weight(1F, false)
                )
                {
                    for (date in weekDates) {
                        Button(onClick = {
                            selectedDay = date.format(formatter)
                            buttonClicked = true
                        },
                            modifier = Modifier
                                .fillMaxWidth(), shape = RectangleShape
                        ) {
                            Text(date.format(formatter).replaceRange(3,date.format(formatter).length, ""), modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        } else {
            Column (horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(.3F)){
                Column (horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(0.dp, 0.dp)
                        .weight(1F)
                )
                {
                    for (date in weekDates) {
                        Button(onClick = {
                            selectedDay = date.format(formatter)
                            buttonClicked = true
                        },
                            modifier = Modifier
                                .weight(1F)
                                .fillMaxWidth(), shape = RectangleShape
                        ) {
                            Text(date.format(formatter).replaceRange(3,date.format(formatter).length, ""), modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
        Column (horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1F)) {
            Text(selectedDay,
                style = MaterialTheme.typography.headlineLarge)
            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .verticalScroll(
                    rememberScrollState()
                )
                .weight(1F, false)){
                for (exercise in exercisesState.value) {
                        Button(onClick = { showDetail = true
                                         selectedExercise = exercise}, modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)) {
                            Text(text = exercise.replace("_", " "))
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