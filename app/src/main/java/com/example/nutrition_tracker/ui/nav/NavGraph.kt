package com.example.nutrition_tracker.ui.nav

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.nutrition_tracker.ui.nav.Screen.Companion.list
import com.example.nutrition_tracker.ui.nav.screens.AddMealScreen

@Preview
@Composable
fun NavGraph(startDestination: String = Screen.Home.route) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (list.map { it.route }.contains(currentDestination))
                BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = startDestination, Modifier.padding(innerPadding)) {
            list.forEach {
                screen -> composable(screen.route) {
                    screen.Render(navController)
                }
            }

            composable("add_meal") { AddMealScreen(navController) }
        }
    }
}