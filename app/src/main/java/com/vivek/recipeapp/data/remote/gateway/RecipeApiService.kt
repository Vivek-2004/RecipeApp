package com.vivek.recipeapp.data.remote.gateway

import com.vivek.recipeapp.data.remote.dto.PopularRecipeResponseDTO
import com.vivek.recipeapp.data.remote.dto.SearchRecipeResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {

    @GET("random")
    suspend fun getPopularRecipes(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("number") number: Int = 10
    ): PopularRecipeResponseDTO

    @GET("complexSearch")
    suspend fun searchRecipe(
        @Query("apiKey") apiKey: String = API_KEY,
        @Query("query") searchQuery: String? = null
    ): SearchRecipeResponseDTO

    companion object {
        const val API_KEY = "a47376ab5cb1482295b5279dd65995a2"
        const val BASE_URL = "https://api.spoonacular.com/recipes/"
    }

}