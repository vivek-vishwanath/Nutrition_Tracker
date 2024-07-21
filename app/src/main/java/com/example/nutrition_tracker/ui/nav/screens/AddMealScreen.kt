package com.example.nutrition_tracker.ui.nav.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nutrition_tracker.data.CSV
import com.example.nutrition_tracker.data.Item
import java.io.BufferedReader
import java.io.InputStreamReader

@OptIn(ExperimentalFoundationApi::class)
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddMealScreen(navController: NavHostController? = null) {
    val csv = CSV(LocalContext.current, "fdc_nutrient_data.csv")

    println(csv[0])

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Item", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController!!.navigateUp() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { contentPadding ->
        var searchText by remember { mutableStateOf("") }
        val foodCatalogue = csv.entries.toTypedArray()
        var foodItems = remember { mutableStateListOf(*foodCatalogue) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding())
        ) {
            // Search bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(128.dp)
                    .padding(16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                TextField(
                    value = searchText,
                    onValueChange = { searchText = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    textStyle = TextStyle(fontSize = 18.sp),
                    shape = RoundedCornerShape(16.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
                    keyboardActions = KeyboardActions(onSearch = {
                        foodItems.clear()
                        foodItems.addAll(foodCatalogue.filter {
                            it["name"]?.contains(
                                searchText,
                                ignoreCase = true
                            ) ?: true
                        })
                    }),
                    label = { Text("Search ...") },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
            val tabData = arrayOf(
                "Items" to Icons.Filled.ShoppingCart,
                "Recipes" to Icons.Filled.Build,
                "Custom" to Icons.Filled.Create
            )
            val pagerState = rememberPagerState { tabData.size }
            val scope = rememberCoroutineScope()
            var selectedIndex by remember { mutableIntStateOf(0) }
            LaunchedEffect(key1 = selectedIndex) {
                pagerState.animateScrollToPage(selectedIndex)
            }
            LaunchedEffect(key1 = pagerState.currentPage) {
                selectedIndex = pagerState.currentPage
            }
            TabRow(
                selectedTabIndex = selectedIndex,
                divider = {
                    Spacer(modifier = Modifier.height(5.dp))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                tabData.forEachIndexed { index, s ->
                    Tab(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(imageVector = s.second, contentDescription = null)
                        },
                        text = {
                            Text(text = s.first)
                        }
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(verticalArrangement = Arrangement.Top) {
                    items(foodItems) {
                        Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Column {
                                Text(
                                    text = it["name"] ?: "",
                                    modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 4.dp)
                                )
                                Text(
                                    text = "Carbs: ${it["Carbohydrates"]}g",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                                Text(
                                    text = "Protein: ${it["Protein"]}g",
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                                )
                                Text(
                                    text = "Fats: ${it["Lipids"]}g",
                                    modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 16.dp)
                                )
                            }
                        }
                    }
                }

            }
        }
    }
}