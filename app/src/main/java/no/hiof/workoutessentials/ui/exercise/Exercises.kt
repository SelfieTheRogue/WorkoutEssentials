package no.hiof.workoutessentials.ui.exercise

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import no.hiof.workoutessentials.R
import no.hiof.workoutessentials.model.Exercise
import no.hiof.workoutessentials.service.api.ApiViewModel

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

                Button(onClick = onDismiss) {
                    Text(text = "Exit")
                }
            }
        }
    }
}
@Composable
fun Exercises() {

    val viewModel: ApiViewModel = viewModel()
    var offset by remember { mutableStateOf(0) }
    var showDetail by remember { mutableStateOf(false) }
    var detailedExercise by remember { mutableStateOf<Exercise?>(null) }

    var exerciseList by remember { mutableStateOf<List<Exercise>>(emptyList()) }

    val data by viewModel.data.observeAsState()

    LaunchedEffect(offset) {
        val exercise = viewModel.fetchData("exercises?offset=$offset")

    }

    DisposableEffect(Unit) {
        viewModel.fetchData("exercises?")

        onDispose { /* Clean-up logic if needed */ }
    }

    if (showDetail && detailedExercise != null) {
        AddDetailDialog(
            exercise = detailedExercise!!,
            onDismiss = { showDetail = false }
        )
    }

    data?.let {
        exerciseList = it
    }

    data class ExerciseItem(val title: String, val image: Int, val route: String)
    val exerciseItems = listOf(
        ExerciseItem("Test 1", R.drawable.ic_launcher_foreground, "Placeholder 1"),
    )

    Column {
        LazyColumn(
            modifier = Modifier
                .weight(5F)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            items(exerciseList) { exercise ->
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .clickable {
                            detailedExercise = exercise
                            showDetail = true
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = exercise.name, style = MaterialTheme.typography.headlineSmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Image(
                            painter = painterResource(exerciseItems[0].image),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
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
            IconButton(onClick = {
                offset += 10
            }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Scroll Forward"
                )
            }
        }
    }
}

@Preview
@Composable
fun ExercisesPreview() {
    Exercises()
}