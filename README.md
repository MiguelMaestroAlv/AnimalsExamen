# Explora Fauna - AnimalsApp

Aplicación móvil nativa para Android desarrollada en **Kotlin** y **Jetpack Compose** que consume la API REST de fauna y ecosistemas (`https://animals.pjasoft.com/api/`). El diseño sigue una estética **Dark Neo-Brutalista** con alto contraste, tarjetas redondeadas con acentos pastel/neón y navegación fluida entre especies y hábitats.

---

## Pantallas y Funcionalidades

1. **Lista de Animales (`AnimalsListScreen`)**
    - Cuadrícula dinámica de 2 columnas con tarjetas contrastadas.
    - Carga asíncrona de fotografías e información básica desde `/api/animals`.
    - Manejo de estados de carga (`CircularProgressIndicator`) y recuperación ante fallos de red mediante botón de reintento.

2. **Detalle de Animal (`AnimalDetailScreen`)**
    - Vista completa con fotografía principal, descripción detallada y listado de hechos interesantes (`facts`).
    - Carrusel horizontal interactivo (`LazyRow`) para explorar la galería de imágenes (`imageGallery`).

3. **Lista de Ambientes (`EnvironmentsListScreen`)**
    - Accesible desde la barra de navegación inferior (`Bottom Navigation Bar`).
    - Presenta los ecosistemas naturales obtenidos desde `/api/environments`.

4. **Detalle de Ambiente (`EnvironmentDetailScreen`)**
    - Muestra la información descriptiva del ecosistema y filtra automáticamente los animales que habitan en él cruzando `environmentId` con `_id`.
    - Permite navegar directamente a la ficha de cada animal listado.

---

## Stack Tecnológico y Arquitectura

- **Lenguaje:** Kotlin
- **Interfaz de Usuario:** Jetpack Compose + Material 3
- **Arquitectura:** MVVM (`AnimalsViewModel` con `State` observable y `viewModelScope`)
- **Consumo de API REST:** Retrofit 2 + Gson Converter
- **Carga de Imágenes:** Coil (`AsyncImage`)
- **Navegación:** Jetpack Navigation Compose (`NavHost` + `NavigationBar`)

---

## Estructura del Proyecto

```text
com.example.animalsapp
├── data
│   ├── model
│   │   ├── Animal.kt
│   │   └── Environment.kt
│   └── network
│       ├── AnimalsApiService.kt
│       └── RetrofitClient.kt
├── ui
│   ├── screens
│   │   ├── AnimalsListScreen.kt
│   │   ├── AnimalDetailScreen.kt
│   │   ├── EnvironmentsListScreen.kt
│   │   └── EnvironmentDetailScreen.kt
│   ├── theme
│   │   ├── Color.kt
│   │   └── Theme.kt
│   └── viewmodel
│       └── AnimalsViewModel.kt
└── MainActivity.kt