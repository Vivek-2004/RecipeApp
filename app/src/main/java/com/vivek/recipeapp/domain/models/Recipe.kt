package com.vivek.recipeapp.domain.models

data class Recipe (
    val id: Int,
    val title: String,
    val image: String,
    val readyInMinutes: Int,
    var isFavourite: Boolean = false
)