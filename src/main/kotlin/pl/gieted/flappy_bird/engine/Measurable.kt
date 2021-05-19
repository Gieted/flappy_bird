package pl.gieted.flappy_bird.engine

interface Measurable {
    var position: Vector2
    val size: Vector2
    
    val bounds: Bounds
        get() = Bounds(
            position.x - size.x / 2,
            position.x + size.x / 2,
            position.y - size.y / 2,
            position.y + size.y / 2
        )
}
