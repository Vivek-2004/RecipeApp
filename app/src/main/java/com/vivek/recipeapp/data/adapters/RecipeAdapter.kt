package com.vivek.recipeapp.data.adapters

import com.vivek.recipeapp.data.local.RecipeEntity
import com.vivek.recipeapp.data.remote.dto.RecipeResponseDTO
import com.vivek.recipeapp.domain.models.Recipe

fun RecipeResponseDTO.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes
    )
}

fun RecipeEntity.toRecipeResponseDTO(): RecipeResponseDTO {
    return RecipeResponseDTO(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes
    )
}


fun RecipeResponseDTO.toRecipe(): Recipe {
    return Recipe(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes
    )
}

fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes
    )
}

fun Recipe.toRecipeEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        image = image,
        title = title,
        readyInMinutes = readyInMinutes
    )
}