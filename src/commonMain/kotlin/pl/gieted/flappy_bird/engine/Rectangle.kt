package pl.gieted.flappy_bird.engine

open class Rectangle : RenderObject() {
    var position: Vector2 = Vector2.zero
    var size: Vector2 = Vector2.defaultSize
    var color: Color = Color.default

    var opacity: Double = 1.0
        set(value) {
            field = limit(value, 0.0, 1.0)
        }
    
    override fun draw(renderer: Renderer, deltaTime: Int) {
        renderer.fill(color.r.toFloat(), color.g.toFloat(), color.b.toFloat(), opacity.toFloat() * 255)
        renderer.stroke(0, 0f)
        renderer.rect(
            (position.x - size.x / 2).toFloat(),
            (-position.y - size.y / 2).toFloat(),
            size.x.toFloat(),
            size.y.toFloat()
        )
    }
}
