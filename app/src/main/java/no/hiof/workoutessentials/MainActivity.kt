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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

import androidx.navigation.compose.rememberNavController
import no.hiof.workoutessentials.Screens.Home.resourceScreenName
import no.hiof.workoutessentials.ui.Exercises
import no.hiof.workoutessentials.ui.Home
import no.hiof.workoutessentials.ui.Login
import no.hiof.workoutessentials.ui.Planner
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
    Home,
    Exercises,
    Planner
}

sealed class Screens(val route: String, @StringRes val resourceScreenName: Int, val icon: ImageVector) {
    object Planner : Screens("planner", R.string.planner, Icons.Default.AddCircle)
    object Home : Screens("home", R.string.home, Icons.Default.Home)
    object Exercises : Screens("exercises", R.string.exercises, Icons.Default.List)
}

val bottomNavigationScreens = listOf(
    Screens.Planner,
    Screens.Home,
    Screens.Exercises
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(){
    val navController = rememberNavController()

    Scaffold(topBar = {
        TopAppBar(title = { Text(stringResource(R.string.workout_essentials),
            textAlign = TextAlign.Center) })
    },
        bottomBar = {
            /*val currentRoute = navController.currentDestination?.route
            if (currentRoute != ScreenNames.Login.name){*/
            BottomNavigationBar(navController, bottomNavigationScreens)//}
        }) {innerPadding ->
        NavHost(navController = navController,
            startDestination = ScreenNames.Login.name,
            Modifier.padding(innerPadding)) {
            composable(ScreenNames.Login.name){Login(login = { navController.navigate(ScreenNames.Home.name)})}
            composable(ScreenNames.Home.name){ Home()}
            composable(ScreenNames.Exercises.name){ Exercises()}
            composable(ScreenNames.Planner.name){ Planner()}
        }
    }
}

@Composable
fun BottomNavigationBar( navController: NavHostController, bottomNavigationScreens: List<Screens>){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    /* If statement to make sure navbar does not appear on the login screen.*/
    if (currentDestination != ScreenNames.Login.name) {
    NavigationBar {

        bottomNavigationScreens.forEach{ screens -> val resourceScreenName = stringResource(screens.resourceScreenName)

                NavigationBarItem(selected = currentDestination == screens.route,
                    onClick = {
                        navController.navigate(screens.route) {
                            /*Make the android back button route to home from different navbar tabs,
                            instead of routing back to the login screen. Pressing back on home
                            will route back to login then close the app after.*/
                            popUpTo(ScreenNames.Home.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = screens.icon,
                            contentDescription = resourceScreenName
                        )
                    },
                    label = { Text(resourceScreenName) })
            }
        }
    }
}
