package no.hiof.workoutessentials.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import no.hiof.workoutessentials.service.AccountService
import no.hiof.workoutessentials.service.StorageService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService) : StorageService{

        //Functions to get or save data to and from firebase
        //override suspend fun getExercises()
        /*TODO Implement get for all exercises*/
        /*TODO Implement get for all exercises linked to user*/

        //override suspend fun save/edit()
        /* TODO Implement the methods to save and edit exercises for the week*/
    }