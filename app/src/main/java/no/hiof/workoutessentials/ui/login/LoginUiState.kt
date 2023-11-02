package no.hiof.workoutessentials.ui.login

import androidx.annotation.StringRes

data class LoginUiState(
    val email: String = "",
    val password: String = "",

    @StringRes val errorMessage: Int = 0
)
