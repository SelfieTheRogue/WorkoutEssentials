package no.hiof.workoutessentials.ui.planner

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.workoutessentials.model.Exercise
import no.hiof.workoutessentials.service.StorageService
import no.hiof.workoutessentials.service.api.ApiViewModel

var exerciseList: List<Exercise>? = null

@SuppressLint("UnrememberedMutableState")
@Composable
fun AddExerciseDialog(
    exerciseList: List<Exercise>,
    onAddExercise: (Exercise) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    var offset by remember { mutableStateOf(0) }
    var showDetail by remember { mutableStateOf(false)}
    var detailedExercise: Exercise? = null

    val viewModel: ApiViewModel = viewModel()

    var orientation = false

    val context = LocalContext.current
    val config = LocalConfiguration.current
    var cardPaddingH = 16.dp
    when (config.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            orientation = true
        }
        else -> {
            orientation = false
        }
    }
    if(orientation) {
        cardPaddingH = 8.dp
    }
    else {
        cardPaddingH = 112.dp
    }

    Dialog(onDismissRequest = onDismiss)
    {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp, cardPaddingH),
            shape = RoundedCornerShape(8.dp)
        ) {
            if (showDetail == true) {
                detailedExercise?.let {
                    Column() {
                        Text(text = "Name: " + it.name)
                        Text(text = "Muscle Group: " + it.muscle)
                        Text(text = "Type: " + it.type)
                        Text(text = "Difficulty: " + it.difficulty)
                        Text(text = "Equipment: " + it.equipment)
                        Text(text = "Instructions")
                    }
                    Divider(thickness = 2.dp, color = Color.Black)
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = it.instructions, modifier = Modifier
                            .verticalScroll(
                                rememberScrollState()
                            )
                            .weight(5F, false))

                        Button(onClick = { showDetail = false }, modifier = Modifier.weight(1F)) {
                            Text(text = "Exit")
                        }
                    }
                }
            }
            else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    //UI for adding exercises
                    LazyColumn(modifier = Modifier.weight(6F)) {
                        items(exerciseList) { exercise ->
                            Row() {
                                Button(
                                    onClick = {
                                        onAddExercise(exercise)
                                        Toast.makeText(context, "added ${exercise.name} to list", Toast.LENGTH_SHORT).show()
                                              },
                                    modifier = Modifier.weight(5F)
                                ) {
                                    Text(text = exercise.name)
                                }
                                Icon(
                                    imageVector = Icons.Default.Info, "Info",
                                    modifier = Modifier
                                        .weight(1F)
                                        .clickable {
                                            detailedExercise = exercise
                                            showDetail = true
                                        })
                            }
                        }
                    }
                    //Arrow Icons for scrolling through list
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .weight(1F),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        IconButton(onClick = {
                            if (offset > 0) {
                                offset -= 10
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Scroll Back"
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        IconButton(onClick = { offset += 10 }) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Scroll Forward"
                            )
                        }
                    }
                    // Confirm and Cancel buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1F),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(onClick = onConfirm) {
                            Text("Confirm")
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = onDismiss) {
                            Text("Cancel")
                        }
                    }
                }
            }
        }
    }
    //Updating list for scrolling
    LaunchedEffect(offset) {
        val exerciseList = viewModel.fetchData("exercises?offset=" + offset.toString())
    }
}


@Composable
fun Planner(storageService: StorageService) {

    val viewModel: ApiViewModel = viewModel()

    var showEditor by remember { mutableStateOf(false) }
    var confirmEdit by remember { mutableStateOf(false) }
    var buttonDay by remember { mutableStateOf("") }

    var addingExerciseList by remember { mutableStateOf<List<Exercise>>(emptyList()) }

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

    LaunchedEffect(confirmEdit) {
        if (confirmEdit) {
            try {
                val exerciseNames = addingExerciseList.map { it.name }
                storageService.saveExercises(buttonDay, exerciseNames)
                Toast.makeText(context, "added exercises to $buttonDay", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                // Handle errors
            } finally {
                // Reset states after the operation is complete
                confirmEdit = false
                addingExerciseList = emptyList()
            }
        }
    }

    val data by viewModel.data.observeAsState()

    DisposableEffect(Unit) {
        viewModel.fetchData("exercises?")

        onDispose { }
    }

    data?.let {
        exerciseList = it
    }

    if (showEditor == true) {
        AddExerciseDialog(
            exerciseList = exerciseList ?: emptyList(),
            onAddExercise = { exercise -> addingExerciseList += exercise },
            onConfirm = {
                confirmEdit = true
                showEditor = false
            },
            onDismiss = { showEditor = false }
        )
    }

    if(orientation) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Mon",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "monday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Tue",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "tuesday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Wed",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "wednesday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Thu",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "thursday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Fri",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "friday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Sat",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "saturday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.weight(1F)) {
                Text(
                    text = "Sun",
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(0.50F)
                )
                Column(
                    modifier = Modifier.weight(1F),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(
                        onClick = {
                            showEditor = true
                            buttonDay = "sunday"
                        },
                        modifier = Modifier
                            .weight(1.0F)
                            .padding(0.dp, 32.dp)
                    ) {
                        Text("Edit")
                    }
                }
            }
        }
    } else {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Mon",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "monday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tue",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "thursday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Wed",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "wednesday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Thu",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "thursday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Fri",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "friday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sat",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "saturday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1.0F)
                        .padding(0.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Sun",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(0.50F)
                    )
                    Column(
                        modifier = Modifier.weight(1F),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                showEditor = true
                                buttonDay = "sunday"
                            },
                            modifier = Modifier
                                .weight(1.0F)
                        ) {
                            Text("Edit")
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PlannerPreview(){
    lateinit var storageService: StorageService
    Planner(storageService)
}