package no.hiof.workoutessentials.service

import no.hiof.workoutessentials.model.User
import kotlinx.coroutines.flow.Flow


interface AccountService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>


    suspend fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    suspend fun createAnonymousAccount()
    suspend fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    suspend fun signOut()
    suspend fun deleteAccount()
}