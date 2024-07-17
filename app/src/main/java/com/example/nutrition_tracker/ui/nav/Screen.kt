package com.example.nutrition_tracker.ui.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.nutrition_tracker.ui.nav.screens.HomeScreen
import com.example.nutrition_tracker.ui.nav.screens.ProfileScreen
import com.example.nutrition_tracker.ui.nav.screens.SearchScreen

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {

    @Composable
    abstract fun Render()

    data object Home : Screen("home", Icons.Default.Home, "Home") {

        @Composable
        override fun Render() = HomeScreen()
    }

    data object Search : Screen("search", Icons.Default.Search, "Search") {

        @Composable
        override fun Render() = SearchScreen()
    }

    data object Profile : Screen("profile", Icons.Default.Person, "Profile") {
        @Composable
        override fun Render() = ProfileScreen()
    }
}