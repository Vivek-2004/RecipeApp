package com.vivek.recipeapp.domain.repository

import com.vivek.recipeapp.domain.models.Recipe
import com.vivek.recipeapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipeRepository {
    suspend fun getAllRecipes(): Flow<Resource<List<Recipe>>>

    suspend fun getPopularRecipes(): Flow<Resource<List<Recipe>>>

    suspend fun getFavouriteRecipes(): Flow<Resource<List<Recipe>>>

    suspend fun insertFavouriteRecipe(recipe: Recipe)

    suspend fun clearFavouriteRecipe(id: Int)

    suspend fun searchRecipes(query: String): Flow<Resource<List<Recipe>>>
}