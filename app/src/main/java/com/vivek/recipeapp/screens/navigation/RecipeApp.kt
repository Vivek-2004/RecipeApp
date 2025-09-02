package com.vivek.recipeapp.screens.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vivek.recipeapp.screens.favourites.FavoriteScreen
import com.vivek.recipeapp.screens.home.HomeScreen

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
                HomeScreen()
            }

            composable(NavigationDestination.Favourite.name) {
                FavoriteScreen()
            }
        }
    }
}