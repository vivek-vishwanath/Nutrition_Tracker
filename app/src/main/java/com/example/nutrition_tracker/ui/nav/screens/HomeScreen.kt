package com.example.nutrition_tracker.ui.nav.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nutrition_tracker.data.Profile

@Composable
fun HomeScreen(navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column {
            val macros = Profile.today.nutrition.macros
            Text(text = "Carbs = ${macros.totalCarb}")
            Text(text = "Fats = ${macros.totalFat}")
            Text(text = "Protein = ${macros.protein}")
            Text("Welcome to Home Screen!", fontSize = 24.sp, textAlign = TextAlign.Center)
        }
        FloatingActionButton(onClick = { navController.navigate("add_meal") }, modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp)) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}