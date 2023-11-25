package no.hiof.workoutessentials.service.impl

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.model.User
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(
    private val auth: FirebaseAuth) : AccountService {
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid, it.isAnonymous) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }
    //authenticate() creates a user with email and password from the text field on login page.
    override suspend fun authenticate(
        email: String,
        password: String,
        onResult: (Throwable?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { onResult(it.exception) }.await()
    }
    //createAnonymousAccount() creates an anonymous account.
    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }
    //linkAccount is currently a createUser function, we could not get the linking to work,
    //linking would have been used for creation and linking anonymous account with email and password.
    override suspend fun linkAccount(
        email: String,
        password: String,
        onResult: (Throwable?) -> Unit
    ) {
        //val credential = EmailAuthProvider.getCredential(email, password)
        //auth.currentUser!!.linkWithCredential(credential).addOnCompleteListener { onResult(it.exception) }.await()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { onResult(it.exception) }.await()
    }
    //signOut() signs out the user.
    override suspend fun signOut() {
        auth.signOut()
    }
    //deleteAccount() called through deleteUserData() from StorageServiceImpl, to delete both user
    //and all information saved with their userID
    override suspend fun deleteAccount() {
        try {
            val user = auth.currentUser
            user?.delete()
        }
        catch (e: Exception){
            println("deleteAccount function has thrown exception: $e")
        }

    }
}