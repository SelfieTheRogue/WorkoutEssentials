package no.hiof.workoutessentials.ui.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import no.hiof.workoutessentials.R
import no.hiof.workoutessentials.ScreenNames
import no.hiof.workoutessentials.isValidEmail
import no.hiof.workoutessentials.isValidPassword
import no.hiof.workoutessentials.service.AccountService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val accountService: AccountService): ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    val isAnonymous = accountService.currentUser.map { it.isAnonymous }

    fun onEmailChange(newValue: String){
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onPasswordChange(newValue: String){
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun onLogin(login: () -> Unit){
        if (!email.isValidEmail()){
            uiState.value = uiState.value.copy(errorMessage = R.string.email_error)
            return
        }
        else if (!password.isValidPassword()){
            uiState.value = uiState.value.copy(errorMessage = R.string.password_error)
            return
        }
        viewModelScope.launch {
            try {
                accountService.authenticate(email, password){ error ->
                    if (error == null)
                        login()
                }
            }
            catch (e: Exception){
                uiState.value = uiState.value.copy(errorMessage = R.string.login_error)
            }
        }
    }
    fun onSignUp(login: () -> Unit){
        if (!email.isValidEmail()){
            uiState.value = uiState.value.copy(errorMessage = R.string.email_error)
            return
        }
        else if (!password.isValidPassword()){
            uiState.value = uiState.value.copy(errorMessage = R.string.password_error)
            return
        }
        viewModelScope.launch {
            try {
                accountService.linkAccount(email,password){error ->
                    if (error == null)
                        login()
                }
            }
            catch (e: Exception){
                uiState.value = uiState.value.copy(errorMessage = R.string.sign_up_error)
            }
        }

    }
    fun onSignOut(){
        viewModelScope.launch {
            accountService.signOut()
        }
    }
    fun onCreateAnonymous(login: () -> Unit){
        viewModelScope.launch {
            try {
                accountService.createAnonymousAccount()
                        login()
            }
            catch (e: Exception){
                uiState.value = uiState.value.copy(errorMessage = R.string.login_error)
            }
        }
    }
    fun onLoggedIn(navController: NavController){
        navController.navigate(ScreenNames.Home.name)
    }
}