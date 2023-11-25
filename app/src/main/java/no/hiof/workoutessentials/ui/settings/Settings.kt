package no.hiof.workoutessentials.ui.settings


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.hiof.workoutessentials.R

@Composable
fun Settings(signOut: () -> Unit,deleteAccount: () -> Unit, viewModel: SettingsViewModel = hiltViewModel()){
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { viewModel.onSignOut(signOut) },
            Modifier
                .padding(16.dp, 8.dp)
                .fillMaxWidth()
                .height(50.dp)) {
            Text(stringResource(R.string.sign_out))
        }
        Button(onClick = { viewModel.onDeleteAccount(deleteAccount)},
            modifier = Modifier
                .padding(16.dp, 8.dp)
                .fillMaxWidth()
                .height(50.dp)) {
            Text(stringResource(R.string.delete_account))
        }
    }

}