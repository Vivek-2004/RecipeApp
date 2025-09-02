package com.vivek.recipeapp.screens.favourites

import com.vivek.recipeapp.domain.models.Recipe

data class FavouriteScreenState(
    val isLoading: Boolean = false,
    val favoriteRecipes: List<Recipe> = emptyList(),
    val error: String = "",
    val isEmpty: Boolean = false
)