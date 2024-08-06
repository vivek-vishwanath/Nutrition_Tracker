package com.example.nutrition_tracker.data

class Day(val date: Int) {

    val nutrition = NutritionLabel()
    val items = ArrayList<Item>()

    operator fun plusAssign(item: Item) {
        items.add(item)
        nutrition += item.nutrition
        println("nutrition = $nutrition")
    }

    constructor() : this(Profile.now)
}