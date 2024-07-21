package com.example.nutrition_tracker.data

data class Item(
    var name: String = "",
    var nutrition: NutritionLabel = NutritionLabel(),
    var servingSize: Measurement = Measurement(100.0, Mass.GRAM),
    var numServings: Int = 1
)

class NutritionLabel(
    val macros: Macronutrients = Macronutrients(),
    val micros: Micronutrients = Micronutrients(),
    val vitamins: Vitamins = Vitamins(),
    val minerals: Minerals = Minerals(),
    val cholesterol: Int = 0
)

class Macronutrients(
    val totalFat: Int = 0, 
    val satFat: Int = 0, 
    val transFat: Int = 0,
    val totalCarb: Int = 0, 
    val fiber: Int = 0, 
    val sugar: Int = 0, 
    val protein: Int = 0
)

class Micronutrients(
    val vitamins: Vitamins = Vitamins(),
    val minerals: Minerals = Minerals()
)

class Vitamins(
    val a: Int = 0,
    val c: Int = 0,
    val d: Int = 0,
    val e: Int = 0,
    val k: Int = 0,
    val b1: Int = 0,
    val b2: Int = 0,
    val b3: Int = 0,
    val b5: Int = 0,
    val b6: Int = 0,
    val b7: Int = 0,
    val b9: Int = 0,
    val b12: Int = 0,
)

class Minerals(
    val calcium: Int = 0,
    val iron: Int = 0,
    val magnesium: Int = 0,
    val phosphorus: Int =0,
    val potassium: Int = 0,
    val sodium: Int = 0,
    val zinc: Int = 0,
    val copper: Int = 0,
    val manganese: Int = 0,
    val selenium: Int = 0,
    val chromium: Int = 0,
    val molybdenum: Int = 0,
    val iodine: Int = 0,
    val chloride: Int = 0
)