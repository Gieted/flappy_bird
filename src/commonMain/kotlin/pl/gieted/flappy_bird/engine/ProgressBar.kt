package pl.gieted.flappy_bird.engine

class ProgressBar : RenderObject() {
    val rectangle = Rectangle().apply { 
        color = Color(248, 183, 51)
    }
    var progressPercentage: Double = 0.0
    
    val height = 10.0

    override fun draw(renderer: Renderer, deltaTime: Int) {
        val width = renderer.width * (progressPercentage / 100)
        rectangle.position = Vector2(-renderer.width / 2 + width / 2, -Vector2.halfScreen.y + 5)
        rectangle.size = Vector2(width, height)
        rectangle.draw(renderer, deltaTime)
    }
}
