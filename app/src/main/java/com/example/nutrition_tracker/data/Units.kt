package com.example.nutrition_tracker.data

class Measurement(val amount: Double, val unit: UnitMeasure) {

    fun convert(other: UnitMeasure) = Measurement(amount * unit.scalar / other.scalar, other)

    fun scale(factor: Double) = Measurement(amount * factor, unit)
}

interface UnitMeasure {
    val scalar: Double
}

enum class Mass(override val scalar: Double): UnitMeasure {

    GRAM(1.0), KILOGRAM(0.001), MILLIGRAM(1000.0),
    OUNCE(28.349523125), POUND(453.59237);
}

enum class Volume(override val scalar: Double): UnitMeasure {

    LITER(1.0), MILLILITER(1000.0),
    FL_OZ(0.0295735295625), CUP(0.236588236), PINT(0.473176472),
    QUART(0.946352944), GALLON(3.78541178),
    TBSP(0.0147867648), TSP(0.0049289216)

}