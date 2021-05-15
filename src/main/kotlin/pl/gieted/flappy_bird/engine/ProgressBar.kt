package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

class ProgressBar(
    renderer: Renderer,
    private val yPosition: Double = Vector2.center.y - 5,
    private val height: Double = 10.0,
    zIndex: Int = 0,
    color: Color = rgb(95, 254, 65)
) :
    Rectangle(
        renderer,
        Vector2(Vector2.center.x, yPosition),
        Vector2(0, height),
        zIndex = zIndex,
        color = color
    ) {

    var progressPercentage: Double = 0.0
        set(value) {
            val extension = Renderer.windowWidth * (value / 100)
            position = Vector2(-Vector2.center.x + extension / 2, yPosition)
            size = Vector2(extension, height)
            field = value
        }
}
