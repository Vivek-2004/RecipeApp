package com.vivek.recipeapp.data.repository

import coil.network.HttpException
import com.vivek.recipeapp.data.adapters.toRecipe
import com.vivek.recipeapp.data.adapters.toRecipeEntity
import com.vivek.recipeapp.data.local.RecipeDatabase
import com.vivek.recipeapp.data.remote.gateway.RecipeApiService
import com.vivek.recipeapp.domain.models.Recipe
import com.vivek.recipeapp.domain.repository.RecipeRepository
import com.vivek.recipeapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    val api: RecipeApiService,
    val db: RecipeDatabase
) : RecipeRepository {

    private val dao = db.recipeDao()

    override suspend fun getAllRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteRecipes = api.searchRecipe().results
                val favouriteIds = dao.getAllFavouritesIds()

                val recipes = remoteRecipes.map { dto ->
                    dto.toRecipe().copy(
                        isFavourite = favouriteIds.contains(dto.id)
                    )
                }

                emit(Resource.Success(recipes))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check internet."))
            } catch (e: HttpException) {
                emit(Resource.Error("Server error: ${e.response.code}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun searchRecipes(query: String): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteRecipes = api.searchRecipe(searchQuery = query).results
                val favouriteIds = dao.getAllFavouritesIds()

                val recipes = remoteRecipes.map { dto ->
                    dto.toRecipe().copy(
                        isFavourite = favouriteIds.contains(dto.id)
                    )
                }

                emit(Resource.Success(recipes))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check internet."))
            } catch (e: HttpException) {
                emit(Resource.Error("Server error: ${e.response.code}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getPopularRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val remoteRecipes = api.getPopularRecipes().recipes
                println(remoteRecipes.toString()) //
                val favouriteIds = dao.getAllFavouritesIds()

                val recipes = remoteRecipes.map { dto ->
                    dto.toRecipe().copy(
                        isFavourite = favouriteIds.contains(dto.id)
                    )
                }
                emit(Resource.Success(recipes))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check internet."))
            } catch (e: HttpException) {
                emit(Resource.Error("Server error: ${e.response.code}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getFavouriteRecipes(): Flow<Resource<List<Recipe>>> {
        return flow {
            emit(Resource.Loading(true))
            try {
                val favourites = dao.getFavouriteRecipes()
                emit(Resource.Success(favourites.map { entity -> entity.toRecipe() }))
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load favourites: ${e.localizedMessage ?: "Unknown error"}"))
            } finally {
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun insertFavouriteRecipe(recipe: Recipe) {
        try {
            dao.addRecipe(recipe = recipe.toRecipeEntity())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun clearFavouriteRecipe(id: Int) {
        try {
            dao.deleteRecipe(id = id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}