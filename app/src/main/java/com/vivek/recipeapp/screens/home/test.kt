package com.vivek.recipeapp.screens.home

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun test(vm: HomeViewModel = hiltViewModel()) {
    Text(vm.state.allRecipes.toString())
}