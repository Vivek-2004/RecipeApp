package com.vivek.recipeapp.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vivek.recipeapp.screens.home.HomeScreen
import com.vivek.recipeapp.screens.home.test

@Composable
fun RecipeApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = NavigationDestination.Home.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavigationDestination.Home.name) {
                HomeScreen(
                    onFavoriteToggle = { recipe ->
//                        recipe.isFavourite = !recipe.isFavourite
                    }
                )
            }

            composable(NavigationDestination.Favourite.name) {
                test()
//                FavoritesScreen(
//                    recipes = recipes.filter { it.isFavorite }
//                )
            }
        }
    }
}