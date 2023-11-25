package no.hiof.workoutessentials.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.service.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService) : StorageService{

        //Function to get exercises for specific day and user from firebase firestore.
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
                }
                else{
                    emptyList()
                }
            }
            catch (e: Exception){
                println("getExercises has thrown exception: $e")
                emptyList()
            }
        }

        //function to save exercises to specific day and user from firebase firestore.
        //Also checks if the document for the userID exists, if it dosent it will create the document and structure.
        override suspend fun saveExercises(day: String, workoutNames: List<String>) {
            val userId = auth.currentUserId
            val documentRef = firestore.collection("users").document(userId)
            val documentSnapshot = documentRef.get().await()
            try {
                if (!documentSnapshot.exists()){
                    val fieldData = hashMapOf(
                        "monday" to emptyList<String>(),
                        "tuesday" to emptyList<String>(),
                        "wednesday" to emptyList<String>(),
                        "thursday" to emptyList<String>(),
                        "friday" to emptyList<String>(),
                        "saturday" to emptyList<String>(),
                        "sunday" to emptyList<String>(),
                    )
                    documentRef.set(fieldData).await()
                    println("UserId document created for user: $userId")
                }
                else{
                    print("Document already exists for user: $userId")
                }
                documentRef.update(day, workoutNames)
                    .await()
            }
            catch(e: Exception) {
                println("saveExercises has thrown exception: $e")
            }
        }
    //deleteUserData() is our function to delete all the users data stored in firebase firestore.
    //It calls deleteAccount() function from AccountServiceImpl.kt to delete the user from firebase authentication.
    override suspend fun deleteUserData() {
        val userId = auth.currentUserId
        try {
            val documentRef = firestore
                .collection("users")
                .document(userId)
            documentRef.delete().await()
            auth.deleteAccount()
        }
        catch (e: Exception){
            println("deleteUserData has thrown exception: $e")
        }
    }
    }