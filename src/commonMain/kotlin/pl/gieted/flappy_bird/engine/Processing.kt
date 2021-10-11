package pl.gieted.flappy_bird.engine

@Suppress("PropertyName")
expect open class Processing() {
    
    fun start()
    
    suspend fun loadImage(path: String): Image

    val height: Int
    
    open val width: Int

    fun background(image: Image)

    fun translate(x: Float, y: Float)

    fun rotate(value: Float)

    fun millis(): Int

    fun background(code: Int)

    fun scale(value: Float)

    val mousePressed: Boolean

    val displayHeight: Int
    val displayWidth: Int

    fun size(x: Int, y: Int, renderer: String)

    open fun setup()

    open fun draw()

    open fun settings()
    
    fun radians(degrees: Float): Float
    
    val P2D: String
    
    fun pushMatrix()
    
    fun popMatrix()
    
    fun noTint()
    
    fun noFill()

    fun fill(v1: Float, v2: Float, v3: Float, alpha: Float)

    fun strokeWeight(stroke: Float)
    
    fun stroke(a: Float, b: Float, c: Float)
    
    fun stroke(a: Int, b: Float)
    
    fun rect(a: Float, b: Float, c: Float, d: Float)
    
    fun tint(color: Int, alpha: Float)
    
    fun image(image: Image, x: Float, y: Float)

    val frameRate: Float
}
