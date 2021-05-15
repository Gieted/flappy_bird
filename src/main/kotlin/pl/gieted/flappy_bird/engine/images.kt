package pl.gieted.flappy_bird.engine

import processing.core.PImage

fun PImage.scale(value: Double) {
    resize((width * value).toInt(), (height * value).toInt())
}
