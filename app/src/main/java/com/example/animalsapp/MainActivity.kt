package com.example.animalsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.animalsapp.ui.screens.*
import com.example.animalsapp.ui.theme.*
import com.example.animalsapp.ui.viewmodel.AnimalsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimalsMainApp()
        }
    }
}

@Composable
fun AnimalsMainApp(animalsViewModel: AnimalsViewModel = viewModel()) {
    val navController = rememberNavController()
    val uiState = animalsViewModel.uiState

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Mostramos la barra inferior en las dos pantallas principales
    val showBottomBar = currentRoute in listOf("animals", "environments")

    Scaffold(
        containerColor = DarkBackground,
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = DarkSurface,
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                ) {
                    NavigationBarItem(
                        selected = currentRoute == "animals",
                        onClick = {
                            navController.navigate("animals") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Pets, contentDescription = "Animales") },
                        label = { Text("Animales") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextDark,
                            selectedTextColor = NeonYellow,
                            indicatorColor = NeonYellow,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )
                    NavigationBarItem(
                        selected = currentRoute == "environments",
                        onClick = {
                            navController.navigate("environments") {
                                popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(Icons.Default.Public, contentDescription = "Ambientes") },
                        label = { Text("Ambientes") },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = TextDark,
                            selectedTextColor = PastelLilac,
                            indicatorColor = PastelLilac,
                            unselectedIconColor = TextGray,
                            unselectedTextColor = TextGray
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "animals",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Pantalla 1: Lista de Animales
            composable("animals") {
                AnimalsListScreen(
                    uiState = uiState,
                    onAnimalClick = { animalId ->
                        navController.navigate("animal_detail/$animalId")
                    },
                    onRetry = { animalsViewModel.fetchData() }
                )
            }

            // Pantalla 2: Lista de Ambientes
            composable("environments") {
                EnvironmentsListScreen(
                    uiState = uiState,
                    onEnvironmentClick = { envId ->
                        navController.navigate("environment_detail/$envId")
                    }
                )
            }

            // Pantalla 3: Detalle del Animal
            composable("animal_detail/{animalId}") { backStackEntry ->
                val animalId = backStackEntry.arguments?.getString("animalId") ?: ""
                val animal = animalsViewModel.getAnimalById(animalId)
                AnimalDetailScreen(
                    animal = animal,
                    onBack = { navController.popBackStack() }
                )
            }

            // Pantalla 4: Detalle del Ambiente
            composable("environment_detail/{environmentId}") { backStackEntry ->
                val envId = backStackEntry.arguments?.getString("environmentId") ?: ""
                val environment = animalsViewModel.getEnvironmentById(envId)
                val animalsInEnv = animalsViewModel.getAnimalsByEnvironment(envId)
                EnvironmentDetailScreen(
                    environment = environment,
                    animalsInEnvironment = animalsInEnv,
                    onAnimalClick = { animalId ->
                        navController.navigate("animal_detail/$animalId")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}