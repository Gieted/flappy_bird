package pl.gieted.flappy_bird.engine

import processing.core.PApplet

@Suppress("PropertyName")
actual open class Processing actual constructor() {
    
    val pApplet = object : PApplet() {
        override fun setup() {
            this@Processing.setup()
        }

        override fun draw() {
            this@Processing.draw()
        }

        override fun settings() {
            this@Processing.settings()
        }
    }

    actual suspend fun loadImage(path: String): Image = pApplet.loadImage(path)

    actual val height: Int
        get() = pApplet.height

    actual val width: Int
        get() = pApplet.width

    actual fun background(image: Image) = pApplet.background(image)

    actual fun background(code: Int) = pApplet.background(code)

    actual fun translate(x: Float, y: Float) = pApplet.translate(x, y)

    actual fun rotate(value: Float) = pApplet.rotate(value)

    actual fun millis(): Int = pApplet.millis()

    actual fun scale(value: Float) = pApplet.scale(value)

    actual val mousePressed: Boolean
        get() = pApplet.mousePressed

    actual val displayHeight: Int
        get() = pApplet.displayHeight

    actual val displayWidth: Int
        get() = pApplet.displayWidth

    actual fun size(x: Int, y: Int, renderer: String) = pApplet.size(x, y, renderer)

    actual open fun setup() {
    }

    actual open fun draw() {
    }

    actual open fun settings() {
    }

    actual fun radians(degrees: Float): Float = PApplet.radians(degrees)

    actual val P2D: String
        get() = PApplet.P2D

    actual fun pushMatrix() = pApplet.pushMatrix()

    actual fun popMatrix() = pApplet.popMatrix()

    actual fun noTint() = pApplet.noTint()

    actual fun noFill() = pApplet.noFill()

    actual fun fill(v1: Float, v2: Float, v3: Float, alpha: Float) = pApplet.fill(v1, v2, v3, alpha)

    actual fun strokeWeight(stroke: Float) = pApplet.strokeWeight(stroke)

    actual fun stroke(a: Float, b: Float, c: Float) = pApplet.stroke(a, b, c)

    actual fun stroke(a: Int, b: Float) = pApplet.stroke(a, b)

    actual fun rect(a: Float, b: Float, c: Float, d: Float) = pApplet.rect(a, b, c, d)

    actual fun tint(color: Int, alpha: Float) = pApplet.tint(color, alpha)

    actual fun image(image: Image, x: Float, y: Float) = pApplet.image(image, x, y)

    actual val frameRate: Float
        get() = pApplet.frameRate

    actual fun start() = PApplet.runSketch(arrayOf("Flappy Bird"), pApplet)
}
