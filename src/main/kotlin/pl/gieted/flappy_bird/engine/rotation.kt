package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

fun Renderer.rotateAroundCenter(center: Vector2, degrees: Double) {
    translate(center.x.toFloat(), center.y.toFloat())
    rotate(degrees.toRadians().toFloat())
    translate(-center.x.toFloat(), -center.y.toFloat())
}
