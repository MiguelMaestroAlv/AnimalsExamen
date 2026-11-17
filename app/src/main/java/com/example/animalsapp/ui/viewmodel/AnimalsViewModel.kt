package com.example.animalsapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.animalsapp.data.model.Animal
import com.example.animalsapp.data.model.Environment
import com.example.animalsapp.data.network.RetrofitClient
import kotlinx.coroutines.launch

// Esta clase guarda los 3 estados posibles de nuestra pantalla:
// 1. Cargando (isLoading = true)
// 2. Listo con datos (animals y environments llenos)
// 3. Error de internet (errorMessage != null)
data class AnimalsUiState(
    val isLoading: Boolean = true,
    val animals: List<Animal> = emptyList(),
    val environments: List<Environment> = emptyList(),
    val errorMessage: String? = null
)

class AnimalsViewModel : ViewModel() {

    // Estado observable: cuando cambie, Jetpack Compose redibujará las pantallas solo
    var uiState by mutableStateOf(AnimalsUiState())
        private set

    // El bloque init se ejecuta automáticamente en cuanto nace el ViewModel
    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, errorMessage = null)
            try {
                // Pedimos ambas listas a la API en segundo plano
                val animalsList = RetrofitClient.apiService.getAnimals()
                val environmentsList = RetrofitClient.apiService.getEnvironments()

                // Guardamos los resultados y quitamos el indicador de carga
                uiState = uiState.copy(
                    isLoading = false,
                    animals = animalsList,
                    environments = environmentsList
                )
            } catch (e: Exception) {
                // Si falla el internet o el servidor, evitamos que la app se cierre (crashee)
                uiState = uiState.copy(
                    isLoading = false,
                    errorMessage = "Error al cargar datos: ${e.localizedMessage}"
                )
            }
        }
    }

    // Funciones de ayuda para nuestras pantallas de detalle:
    fun getAnimalById(id: String): Animal? {
        return uiState.animals.find { it.id == id }
    }

    fun getEnvironmentById(id: String): Environment? {
        return uiState.environments.find { it.id == id }
    }

    fun getAnimalsByEnvironment(environmentId: String): List<Animal> {
        return uiState.animals.filter { it.environmentId == environmentId }
    }
}