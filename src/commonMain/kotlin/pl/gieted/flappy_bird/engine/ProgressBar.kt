package pl.gieted.flappy_bird.engine

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

    override fun draw() {
        val width = renderer.width * (progressPercentage / 100)
        position = Vector2(-renderer.width / 2 + width / 2, yPosition)
        size = Vector2(width, height)
        super.draw()
    }
}
