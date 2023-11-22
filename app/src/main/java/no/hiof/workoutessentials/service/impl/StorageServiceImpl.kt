package no.hiof.workoutessentials.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.service.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService) : StorageService{

        //Functions to get or save data to and from firebase
        override suspend fun getExercises(day: String): List<String>{
            val userId = auth.currentUserId

            return try {
                val documentSnapshot = firestore.collection("users")
                    .document(userId)
                    .get()
                    .await()

                if (documentSnapshot.exists()){
                    val dayExercises = documentSnapshot.get(day)
                    return dayExercises as List<String>
                } else{
                    emptyList()
                }
            } catch (e: Exception){
                emptyList()
                //add logging
            }
        }

        override suspend fun saveExercises(day: String, workoutNames: List<String>) {
            val userId = auth.currentUserId

            try {
                val documentRef = firestore.collection("users").document(userId)
                documentRef.update(day, workoutNames)
                    .await()
            } catch(e: Exception) {
                //add logging
            }
        }
        /*TODO Implement get for all exercises linked to user*/

        //override suspend fun save/edit()
        /* TODO Implement the methods to save and edit exercises for the week*/
    }