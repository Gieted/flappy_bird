package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer

class ProgressBar(
    renderer: Renderer,
    private val yPosition: Double = -Vector2.halfScreen.y + 5,
    private val height: Double = 10.0,
    zIndex: Int = 0,
    color: Color = rgb(95, 254, 65)
) : Rectangle(
        renderer,
        Vector2(Vector2.halfScreen.x, yPosition),
        zIndex = zIndex,
        color = color,
        size = Vector2(0, height),
    ) {

    var progressPercentage: Double = 0.0
        set(value) {
            val width = Renderer.windowWidth * (value / 100)
            position = Vector2(-Vector2.halfScreen.x + width / 2, yPosition)
            size = Vector2(width, height)
            field = value
        }
}
