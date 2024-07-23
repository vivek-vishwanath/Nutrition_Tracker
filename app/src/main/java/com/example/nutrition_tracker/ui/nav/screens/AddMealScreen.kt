package com.example.nutrition_tracker.ui.nav.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.nutrition_tracker.data.CSV
import com.example.nutrition_tracker.data.Item
import com.example.nutrition_tracker.data.Macro
import com.example.nutrition_tracker.data.Macronutrients
import com.example.nutrition_tracker.data.NutritionLabel

typealias JSON = HashMap<String, String>

@OptIn(ExperimentalFoundationApi::class)
@Preview
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddMealScreen(navController: NavHostController? = null) {
    val csv = CSV(LocalContext.current, "fdc_nutrient_data.csv")
    val foodCatalogue = csv.entries.toTypedArray()

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
        val foodItems = remember { mutableStateListOf(*foodCatalogue) }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = contentPadding.calculateTopPadding()),
        ) {
            val tabHeader = arrayOf(
                "Items" to Icons.Filled.ShoppingCart,
                "Recipes" to Icons.Filled.Build
            )
            val pagerState = rememberPagerState { tabHeader.size }

            SearchBar(items = foodItems, catalogue = foodCatalogue)
            TabHeader(tabHeader, pagerState)
            HorizontalPager(state = pagerState) {
                ItemList(foodItems)
            }
        }
    }
}

@Composable
fun SearchBar(items: SnapshotStateList<JSON>, catalogue: Array<JSON>) {
    var searchText by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
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
                items.clear()
                items.addAll(catalogue.filter {
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
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabHeader(tabHeader: Array<Pair<String, ImageVector>>, pagerState: PagerState) {
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
        tabHeader.forEachIndexed { index, s ->
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
}

@Composable
fun ItemList(items: List<JSON>) {
    Box(modifier = Modifier
        .fillMaxHeight()
        .padding(top = 16.dp)) {
        LazyColumn(verticalArrangement = Arrangement.Top) {
            items(items) { obj ->
                val item = Item(
                    name = obj["name"]!!,
                    nutrition = NutritionLabel(
                        macros = Macronutrients(
                            totalCarb = obj["Carbohydrates"]?.toTenthsDecimal() ?: 0,
                            totalFat = obj["Lipids"]?.toTenthsDecimal() ?: 0,
                            protein = obj["Protein"]?.toTenthsDecimal() ?: 0
                        )
                    )
                )
                ItemCard(item)
            }
        }
    }

}

@Composable
fun ItemCard(item: Item) {
    Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Column {
            Text(
                text = item.name,
                modifier = Modifier.padding(16.dp, 16.dp, 16.dp, 4.dp)
            )
            Row {
                MacroBar(item)
            }
        }
    }

}

@Composable
fun RowScope.MacroBar(item: Item) {
    arrayOf(
        Macro("Carbs", item.nutrition.macros.totalCarb, Color.Yellow, 16.dp, 2.dp, 16.dp, 0.dp, 0.dp, 16.dp),
        Macro("Protein", item.nutrition.macros.protein, Color.Green, 2.dp, 2.dp),
        Macro("Fats", item.nutrition.macros.totalFat, Color.LightGray, 2.dp, 16.dp, 0.dp, 16.dp, 16.dp, 0.dp)
    ).forEach {
        val total = item.nutrition.macros.total.toDouble()
        println("\tn = ${it.amount}")
        val weight = if (it.amount / total < 0.2) {
            0.2f
        } else (it.amount / total).toFloat()
        Column(
            modifier = Modifier
                .padding(
                    start = it.startPadding,
                    end = it.endPadding
                )
                .weight(weight)
        ) {
            Box(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .clip(
                        RoundedCornerShape(
                            topStart = CornerSize(it.topLeft),
                            topEnd = CornerSize(it.topRight),
                            bottomEnd = CornerSize(it.bottomLeft),
                            bottomStart = CornerSize(it.bottomRight)
                        )
                    )
                    .background(it.color),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(
                    text = "${it.amount / 10.0}g",
                    fontSize = 10.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            Text(
                text = it.name,
                fontSize = 9.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp, bottom = 8.dp)
            )
        }
    }
}

fun String.toTenthsDecimal() = toDoubleOrNull()?.times(10)?.toInt()