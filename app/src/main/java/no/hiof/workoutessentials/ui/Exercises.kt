package no.hiof.workoutessentials.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import no.hiof.workoutessentials.R


@Composable
fun Exercises() {
    /*Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "This is the Exercises Screen",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center)
        Text(text = "Will contain list of all exercises available through API",
            style =MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center)
    }*/
    data class ExerciseItem(val title: String, val image: Int, val route: String)

    val exerciseItems = listOf(
        ExerciseItem("Test 1",R.drawable.ic_launcher_foreground, "Placeholder 1"),
        ExerciseItem("Test 2",R.drawable.ic_launcher_foreground, "Placeholder 2"),
        ExerciseItem("Test 3",R.drawable.ic_launcher_foreground, "Placeholder 3"),
        ExerciseItem("Test 4",R.drawable.ic_launcher_foreground, "Placeholder 4"),
        ExerciseItem("Test 5",R.drawable.ic_launcher_foreground, "Placeholder 5")
    )
    LazyColumn(modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center){
        items(exerciseItems) { item ->
            Text(text = item.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(2.dp))
            Box(modifier = Modifier
                .size(150.dp)
                .clickable {
                    /* Navigation per picture*/
                })
            {
                Image(painter = painterResource(item.image),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Preview
@Composable
fun ExercisesPreview(){
    Exercises()
}