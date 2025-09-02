package com.vivek.recipeapp.data.remote.dto

data class RecipeResponseDTO (
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int
)