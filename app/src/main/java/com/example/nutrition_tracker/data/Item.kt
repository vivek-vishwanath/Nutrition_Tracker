package com.example.nutrition_tracker.data

data class Item(
    var name: String = "",
    var nutrition: NutritionLabel = NutritionLabel(),
    var servingSize: Measurement = Measurement(100.0, Mass.GRAM),
    var numServings: Int = 1
)

data class NutritionLabel(
    var macros: Macronutrients = Macronutrients(),
    var micros: Micronutrients = Micronutrients(),
    var vitamins: Vitamins = Vitamins(),
    var minerals: Minerals = Minerals(),
    var cholesterol: Int = 0
) {

    operator fun plusAssign(nutrition: NutritionLabel) {
        macros += nutrition.macros
        micros += nutrition.micros
        vitamins += nutrition.vitamins
        minerals += nutrition.minerals
        cholesterol += nutrition.cholesterol
        println("macros = $macros")
    }
}

data class Macronutrients(
    var totalFat: Int = 0, 
    var satFat: Int = 0, 
    var transFat: Int = 0,
    var totalCarb: Int = 0, 
    var fiber: Int = 0, 
    var sugar: Int = 0, 
    var protein: Int = 0
) {
    val total get() = totalFat + totalCarb + protein

    operator fun plusAssign(macros: Macronutrients) {
        totalFat += macros.totalFat
        satFat += macros.satFat
        transFat += macros.transFat
        totalCarb += macros.totalCarb
        fiber += macros.fiber
        sugar += macros.sugar
        protein += macros.protein
    }
}

data class Micronutrients(
    var vitamins: Vitamins = Vitamins(),
    var minerals: Minerals = Minerals()
) {
    operator fun plusAssign(micros: Micronutrients) {
        vitamins += micros.vitamins
        minerals += micros.minerals
    }
}

data class Vitamins(
    var a: Int = 0,
    var c: Int = 0,
    var d: Int = 0,
    var e: Int = 0,
    var k: Int = 0,
    var b1: Int = 0,
    var b2: Int = 0,
    var b3: Int = 0,
    var b5: Int = 0,
    var b6: Int = 0,
    var b7: Int = 0,
    var b9: Int = 0,
    var b12: Int = 0,
) {
    operator fun plusAssign(vitamins: Vitamins) {
        a += vitamins.a
        c += vitamins.c
        d += vitamins.d
        e += vitamins.e
        k += vitamins.k
        b1 += vitamins.b1
        b2 += vitamins.b2
        b3 += vitamins.b3
        b5 += vitamins.b5
        b6 += vitamins.b6
        b7 += vitamins.b7
        b9 += vitamins.b9
        b12 += vitamins.b12
    }
}

data class Minerals(
    var calcium: Int = 0,
    var iron: Int = 0,
    var magnesium: Int = 0,
    var phosphorus: Int =0,
    var potassium: Int = 0,
    var sodium: Int = 0,
    var zinc: Int = 0,
    var copper: Int = 0,
    var manganese: Int = 0,
    var selenium: Int = 0,
    var chromium: Int = 0,
    var molybdenum: Int = 0,
    var iodine: Int = 0,
    var chloride: Int = 0
) {
    operator fun plusAssign(minerals: Minerals) {
        calcium += minerals.calcium
        iron += minerals.iron
        magnesium += minerals.magnesium
        phosphorus += minerals.phosphorus
        sodium += minerals.sodium
        zinc += minerals.zinc
        copper += minerals.copper
        manganese += minerals.manganese
        selenium += minerals.selenium
        chromium += minerals.chromium
        molybdenum += minerals.molybdenum
        iodine += minerals.iodine
        chloride += minerals.chloride
    }
}