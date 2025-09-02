package com.vivek.recipeapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addRecipe(recipe: RecipeEntity)

    @Query("SELECT * FROM recipes")
    fun getFavouriteRecipes(): List<RecipeEntity>

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipe(id: Int)

    @Query("SELECT id FROM recipes")
    suspend fun getAllFavouritesIds(): List<Int>
}