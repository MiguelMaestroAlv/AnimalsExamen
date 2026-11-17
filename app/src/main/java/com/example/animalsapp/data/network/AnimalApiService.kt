package com.example.animalsapp.data.network

import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import retrofit2.http.GET

interface AnimalsApiService {
    @GET("animals")
    suspend fun getAnimals(): List<Animal>

    @GET("environments")
    suspend fun getEnvironments(): List<Environment>
}
