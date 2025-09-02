package com.vivek.recipeapp.screens.navigation

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    if (currentRoute == NavigationDestination.Home.name) Icons.Filled.Home else Icons.Outlined.Home,
                    contentDescription = NavigationDestination.Home.name,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text(NavigationDestination.Home.name, fontSize = 12.sp) },
            selected = currentRoute == NavigationDestination.Home.name,
            onClick = { navController.navigate(NavigationDestination.Home.name) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFFF6B35),
                selectedTextColor = Color(0xFFFF6B35),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            icon = {
                Icon(
                    if (currentRoute == NavigationDestination.Favourite.name) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = NavigationDestination.Favourite.name,
                    modifier = Modifier.size(28.dp)
                )
            },
            label = { Text(NavigationDestination.Favourite.name, fontSize = 12.sp) },
            selected = currentRoute == NavigationDestination.Favourite.name,
            onClick = { navController.navigate(NavigationDestination.Favourite.name) },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color(0xFFFF6B35),
                selectedTextColor = Color(0xFFFF6B35),
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}