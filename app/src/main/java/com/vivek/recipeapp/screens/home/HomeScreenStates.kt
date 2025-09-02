package com.vivek.recipeapp.screens.home

import com.vivek.recipeapp.data.remote.dto.RecipeResponseDTO
import com.vivek.recipeapp.domain.models.Recipe

data class HomeScreenStates(
    val isLoading: Boolean = false,
    val allRecipes: List<Recipe> = emptyList(),
    val popularRecipes: List<Recipe> = emptyList(),
    val isSearching: Boolean = false,
    val error: String = ""
)