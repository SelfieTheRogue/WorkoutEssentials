package no.hiof.workoutessentials

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.navigation.compose.rememberNavController
import no.hiof.workoutessentials.ui.Home
import no.hiof.workoutessentials.ui.Login
import no.hiof.workoutessentials.ui.theme.WorkoutEssentialsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutEssentialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation()
                }
            }
        }
    }
}

enum class ScreenNames{
    Login,
    Home
}

sealed class Screens(val route: String, @StringRes val resourceId: Int) {
    object Home : Screens("home", R.string.home)
}

val listOfScreens = listOf(
    Screens.Home
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.workout_essentials)) })
    },
        bottomBar = {

        }) {innerPadding ->
        NavHost(navController = navController,
            startDestination = ScreenNames.Login.name,
            Modifier.padding(innerPadding)) {
            composable(ScreenNames.Login.name){Login(login = { navController.navigate(ScreenNames.Home.name)})}
            composable(ScreenNames.Home.name){ Home()}
        }
    }


}
