package pl.gieted.flappy_bird.engine

data class Vector2(val x: Double, val y: Double) {

    constructor(x: Int, y: Int) : this(x.toDouble(), y.toDouble())
    constructor(x: Int, y: Double) : this(x.toDouble(), y)
    constructor(x: Double, y: Int) : this(x, y.toDouble())

    operator fun plus(other: Vector2) = Vector2(this.x + other.x, this.y + other.y)
    operator fun minus(other: Vector2) = Vector2(this.x - other.x, this.y - other.y)
    operator fun times(number: Double) = Vector2(this.x * number, this.y * number)
    operator fun times(number: Int) = this / number.toDouble()
    operator fun div(number: Double) = Vector2(this.x / number, this.y / number)
    operator fun div(number: Int) = this / number.toDouble()

    companion object {
        val one = Vector2(1, 1)
        val zero = Vector2(0, 0)
        val screen = Vector2(Renderer.defaultWidth, Renderer.defaultHeight)
        val halfScreen = Vector2(Renderer.defaultWidth / 2.0, Renderer.defaultHeight / 2.0)
        val defaultSize = Vector2(50, 50)
    }
}
