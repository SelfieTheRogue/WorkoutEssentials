package no.hiof.workoutessentials.service.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import no.hiof.workoutessentials.model.Exercise
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiViewModel : ViewModel() {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    private val _data = MutableLiveData<List<Exercise>?>(null)
    val data: LiveData<List<Exercise>?> = _data

    fun fetchData(url: String) {
        viewModelScope.launch {
            try {
                val response = apiService.getExercise(url)
                if (response.isSuccessful) {
                    _data.value = response.body()
                }
            } catch (e: Exception) {
                // error
            }
        }
    }
}