package com.example.animalsapp.data.model

data class Animal(
    val id: String,
    val name: String,
    val image: String,
    val description: String,
    val imageGallery: List<String> = emptyList(),
    val environmentId: String = "",
    val facts: List<String> = emptyList()
)
