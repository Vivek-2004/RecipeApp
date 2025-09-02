package com.vivek.recipeapp.screens.favourites

import com.vivek.recipeapp.domain.models.Recipe

sealed class FavouriteScreenEvent {
    object LoadFavorites : FavouriteScreenEvent()
    object Refresh : FavouriteScreenEvent()
    data class RemoveFromFavorites(val recipe: Recipe) : FavouriteScreenEvent()
    data class ToggleFavorite(val recipe: Recipe) : FavouriteScreenEvent()
}