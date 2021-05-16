package pl.gieted.flappy_bird.engine

import processing.core.PImage

fun <T : Comparable<T>> limit(value: T, lowerBound: T? = null, upperBound: T? = null) = when {
    upperBound != null && value > upperBound -> upperBound
    lowerBound != null && value < lowerBound -> lowerBound
    else -> value
}

fun PImage.scale(value: Double) {
    resize((width * value).toInt(), (height * value).toInt())
}

fun rgb(r: Int, g: Int, b: Int) = Color(r, g, b)

