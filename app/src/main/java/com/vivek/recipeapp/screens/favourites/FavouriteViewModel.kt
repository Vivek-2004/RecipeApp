package com.vivek.recipeapp.screens.favourites

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.recipeapp.domain.models.Recipe
import com.vivek.recipeapp.domain.repository.RecipeRepository
import com.vivek.recipeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    var state by mutableStateOf(FavouriteScreenState())
        private set

    init {
        loadFavoriteRecipes()
    }

    fun onEvent(event: FavouriteScreenEvent) {
        when (event) {
            is FavouriteScreenEvent.LoadFavorites -> {
                loadFavoriteRecipes()
            }

            is FavouriteScreenEvent.Refresh -> {
                loadFavoriteRecipes()
            }

            is FavouriteScreenEvent.RemoveFromFavorites -> {
                removeFromFavorites(event.recipe)
            }

            is FavouriteScreenEvent.ToggleFavorite -> {
                toggleFavorite(event.recipe)
            }
        }
    }

    private fun loadFavoriteRecipes() {
        viewModelScope.launch {
            repository
                .getFavouriteRecipes()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { recipes ->
                                state = state.copy(
                                    favoriteRecipes = recipes,
                                    error = "",
                                    isEmpty = recipes.isEmpty()
                                )
                            }
                            Log.d(
                                "FavoriteScreen",
                                "Favorite recipes loaded: ${state.favoriteRecipes.size}"
                            )
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "Unknown error occurred",
                                isLoading = false
                            )
                            Log.e(
                                "FavoriteScreen",
                                "Error loading favorite recipes: ${result.message}"
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun removeFromFavorites(recipe: Recipe) {
        viewModelScope.launch {
            try {
                repository.clearFavouriteRecipe(recipe.id)
                val updatedFavorites = state.favoriteRecipes.filter { it.id != recipe.id }
                state = state.copy(
                    favoriteRecipes = updatedFavorites,
                    isEmpty = updatedFavorites.isEmpty()
                )
            } catch (e: Exception) {
                state = state.copy(error = "Failed to remove from favorites")
            }
        }
    }

    private fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            try {
                if (recipe.isFavourite) {
                    repository.clearFavouriteRecipe(recipe.id)
                } else {
                    repository.insertFavouriteRecipe(recipe)
                }
                loadFavoriteRecipes()

            } catch (e: Exception) {
                state = state.copy(error = "Failed to update favorite")
            }
        }
    }
}