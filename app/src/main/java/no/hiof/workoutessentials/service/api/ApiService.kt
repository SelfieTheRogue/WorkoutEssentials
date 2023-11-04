package no.hiof.workoutessentials.service.api

import no.hiof.workoutessentials.model.Exercise
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("X-api-key: " + "btrELspKjbq8prveP0Nmrw==l9zJav4bomhUcbRP")
    @GET("exercises?muscle=biceps")
    suspend fun getExercise(): Response<List<Exercise>>
}