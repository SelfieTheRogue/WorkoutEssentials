package no.hiof.workoutessentials.service

interface StorageService {

    suspend fun getExercises(day: String) : List<String>
    suspend fun saveExercises(day: String, workoutNames: List<String>)


}