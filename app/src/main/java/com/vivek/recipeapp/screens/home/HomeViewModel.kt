package com.vivek.recipeapp.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vivek.recipeapp.data.remote.gateway.RecipeApiService
import com.vivek.recipeapp.data.local.RecipeDatabase
import com.vivek.recipeapp.domain.repository.RecipeRepository
import com.vivek.recipeapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val repo: RecipeRepository
) : ViewModel() {

    var state by mutableStateOf(HomeScreenStates())

    init {
        getAllRecipes()
    }

    private fun getAllRecipes() {
        viewModelScope.launch {
            repo.getAllRecipes().collect { result ->
                when(result) {
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

    private fun getAllRecipes(query: String = "") {
        viewModelScope.launch {
            val searchQuery = query.trim().lowercase()

            repository
                .getAllRecipies(searchQuery.ifEmpty { "chicken" }) // Default to chicken if empty
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            result.data?.let { recipies ->
                                state = state.copy(
                                    recipes = recipies,
                                    error = ""
                                )
                            }
                            Log.d("HomeScreen", "Recipes loaded: ${state.recipes.size}")
                        }
                        is Resource.Error -> {
                            state = state.copy(
                                error = result.message ?: "Unknown error occurred",
                                isLoading = false
                            )
                            Log.e("HomeScreen", "Error loading recipes: ${result.message}")
                        }
                        is Resource.Loading -> {
                            state = state.copy(isLoading = result.isLoading)
                        }
                    }
                }
        }
    }

}