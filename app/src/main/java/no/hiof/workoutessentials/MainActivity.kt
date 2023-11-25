package no.hiof.workoutessentials

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import no.hiof.workoutessentials.service.StorageService
import no.hiof.workoutessentials.ui.exercise.Exercises
import no.hiof.workoutessentials.ui.home.Home
import no.hiof.workoutessentials.ui.login.Login
import no.hiof.workoutessentials.ui.planner.Planner
import no.hiof.workoutessentials.ui.settings.Settings
import no.hiof.workoutessentials.ui.theme.WorkoutEssentialsTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var storageService: StorageService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkoutEssentialsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(storageService)
                }
            }
        }

    }
}

enum class ScreenNames{
    Login,
    Home,
    Exercises,
    Planner,
    Settings
}

sealed class Screens(val route: String, @StringRes val resourceScreenName: Int, val icon: ImageVector) {
    object Home : Screens("home", R.string.home, Icons.Default.Home)
    object Planner : Screens("planner", R.string.planner, Icons.Default.AddCircle)
    object Exercises : Screens("exercises", R.string.exercises, Icons.Default.List)
    object Settings : Screens("settings", R.string.settings, Icons.Default.Settings)
}

val bottomNavigationScreens = listOf(
    Screens.Home,
    Screens.Planner,
    Screens.Exercises,
    Screens.Settings
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Navigation(storageService: StorageService){
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = { Text(stringResource(R.string.workout_essentials),
            textAlign = TextAlign.Center) }, Modifier.padding(4.dp)
            )
    },
        /*If statements used to change start destination dependent on current user, if user is logged in
            the start destination will be the home screen and login screen if not logged in.*/
        bottomBar = {
            BottomNavigationBar(navController, bottomNavigationScreens)//}
        }) {innerPadding ->
        if (auth.currentUser == null){
            NavHost(navController = navController,
                startDestination = ScreenNames.Login.name,
                Modifier.padding(innerPadding)) {
                composable(ScreenNames.Login.name){ Login(login = { navController.navigate(ScreenNames.Home.name)}) }
                composable(ScreenNames.Home.name){ Home(storageService) }
                composable(ScreenNames.Exercises.name){ Exercises() }
                composable(ScreenNames.Planner.name){ Planner(storageService) }
                composable(ScreenNames.Settings.name){ Settings(signOut = {navController.navigate(ScreenNames.Login.name)},
                    deleteAccount = {navController.navigate(ScreenNames.Login.name)})}
        }
        }
        else{
            NavHost(navController = navController,
                startDestination = ScreenNames.Home.name,
                Modifier.padding(innerPadding)) {
                composable(ScreenNames.Login.name){ Login(login = { navController.navigate(ScreenNames.Home.name)}) }
                composable(ScreenNames.Home.name){ Home(storageService) }
                composable(ScreenNames.Exercises.name){ Exercises() }
                composable(ScreenNames.Planner.name){ Planner(storageService) }
                composable(ScreenNames.Settings.name){ Settings(signOut = {navController.navigate(ScreenNames.Login.name)},
                    deleteAccount = {navController.navigate(ScreenNames.Login.name)})}
            }
        }
    }
}

@Composable
fun BottomNavigationBar( navController: NavHostController, bottomNavigationScreens: List<Screens>){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val auth = FirebaseAuth.getInstance()


    /* If statement to make sure navbar does not appear on the login screen.*/
    if (auth.currentUser != null || currentDestination != ScreenNames.Login.name) {
    NavigationBar {
        bottomNavigationScreens.forEach{ screens -> val resourceScreenName = stringResource(screens.resourceScreenName)
                NavigationBarItem(
                    selected = currentDestination == screens.route,
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
                    label = {Text(resourceScreenName)}
                )
            }
        }
    }
}
