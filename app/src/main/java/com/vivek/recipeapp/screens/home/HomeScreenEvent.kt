package com.vivek.recipeapp.screens.home

import com.vivek.recipeapp.domain.models.Recipe

sealed class HomeScreenEvent {
    object Refresh : HomeScreenEvent()
    object LoadPopularRecipes : HomeScreenEvent()
    data class OnSearchQuery(val query: String) : HomeScreenEvent()
    data class ToggleFavorite(val recipe: Recipe) : HomeScreenEvent()
    object ClearSearch : HomeScreenEvent()
}