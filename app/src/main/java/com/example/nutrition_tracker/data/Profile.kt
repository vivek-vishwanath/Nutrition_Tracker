package com.example.nutrition_tracker.data

import java.time.Instant
import java.util.Stack

object Profile {

    val days = Stack<Day>()

    val today: Day
        get() = if (days.isNotEmpty() && days.peek().date == now) days.peek()
        else {
            days.push(Day())
            days.peek()
        }
//    val today = Day()

    val now get() = (Instant.now().epochSecond / 86400).toInt()

    operator fun plusAssign(item: Item) {
        today += item
    }

    init {
        println("Initializing Profile")
    }
}