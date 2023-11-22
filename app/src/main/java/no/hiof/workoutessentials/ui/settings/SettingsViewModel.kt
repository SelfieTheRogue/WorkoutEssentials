package no.hiof.workoutessentials.ui.settings


import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.hiof.workoutessentials.service.AccountService
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val accountService: AccountService): ViewModel(){

    fun onSignOut(signOut: () -> Unit){
        viewModelScope.launch {
            try {
                accountService.signOut()
                signOut()
            }
            catch (e: Exception){

            }
        }
    }
}