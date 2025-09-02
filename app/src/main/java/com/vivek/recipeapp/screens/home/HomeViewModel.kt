package com.vivek.recipeapp.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.recipeapp.domain.models.Recipe
import com.vivek.recipeapp.domain.repository.RecipeRepository
import com.vivek.recipeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: RecipeRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenStates())

    private var searchJob: Job? = null

    init {
        getAllRecipes()
    }

    fun onEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.Refresh -> {
                getPopularRecipes()
                getAllRecipes(state.searchQuery)
            }

            is HomeScreenEvent.LoadPopularRecipes -> {
                getPopularRecipes()
            }

            is HomeScreenEvent.OnSearchQuery -> {
                state =
                    state.copy(searchQuery = event.query, isSearching = event.query.isNotBlank())
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L) // Debounce search
                    getAllRecipes(event.query)
                }
            }

            is HomeScreenEvent.ToggleFavorite -> {
                toggleFavorite(event.recipe)
            }

            is HomeScreenEvent.ClearSearch -> {
                state = state.copy(searchQuery = "", isSearching = false)
                getAllRecipes("")
            }
        }
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            repo.getAllRecipes().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        result.data?.let { recipes ->
                            state = state.copy(
                                allRecipes = recipes,
                                error = ""
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(
                            error = result.message ?: "Unknown error occurred",
                            isLoading = false
                        )
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                }
            }
        }
    }

    private fun getAllRecipes(query: String? = null) {
        viewModelScope.launch {
            val searchQuery = query?.trim()?.lowercase()

            repo.searchRecipes(searchQuery?.ifEmpty { "chicken" } ?: "fish")
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { recipes ->
                                state = state.copy(
                                    allRecipes = recipes,
                                    error = ""
                                )
                            }
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "Unknown error occurred",
                                isLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun getPopularRecipes() {
        viewModelScope.launch {
            repo
                .getPopularRecipes()
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { recipes ->
                                state = state.copy(
                                    popularRecipes = recipes,
                                    error = ""
                                )
                            }
                        }

                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "Unknown error occurred",
                                isPopularRecipesLoading = false
                            )
                        }

                        is Resource.Loading -> {
                            state = state.copy(isPopularRecipesLoading = result.isLoading)
                        }
                    }
                }
        }
    }

    private fun toggleFavorite(recipe: Recipe) {
        viewModelScope.launch {
            try {
                if (recipe.isFavourite) {
                    repo.clearFavouriteRecipe(recipe.id)
                } else {
                    repo.insertFavouriteRecipe(recipe)
                }

                // Update local state immediately for better UX
                val updatedRecipes = state.allRecipes.map {
                    if (it.id == recipe.id) it.copy(isFavourite = !it.isFavourite) else it
                }
                val updatedPopularRecipes = state.popularRecipes.map {
                    if (it.id == recipe.id) it.copy(isFavourite = !it.isFavourite) else it
                }

                state = state.copy(
                    allRecipes = updatedRecipes,
                    popularRecipes = updatedPopularRecipes
                )

            } catch (e: Exception) {
                state = state.copy(error = "Failed to update favorite")
            }
        }
    }

}