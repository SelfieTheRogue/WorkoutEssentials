package no.hiof.workoutessentials.ui.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.service.StorageService
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService
): ViewModel(){

    //onSignOut() calls the signOut() function from AccountServiceImpl.kt and calls the navigation in
    //main activity to route back to login page.
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
    //onDeleteAccount() calls the deleteUserData() function from StorageServiceImpl.kt to delete user,
    //and signs it out. Deleting the account in the process, then routing back to login page.
    fun onDeleteAccount(deleteAccount: () -> Unit){
        viewModelScope.launch {
            try {
                storageService.deleteUserData()
                accountService.signOut()
                deleteAccount()
            }
            catch (e: Exception){
                println("onDeleteAccount has thrown exception: $e")
            }
        }
    }
}