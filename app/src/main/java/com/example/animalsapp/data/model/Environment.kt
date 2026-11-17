package com.example.animalsapp.data.model

import com.google.gson.annotations.SerializedName

data class Environment(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val image: String,
    val description: String
)