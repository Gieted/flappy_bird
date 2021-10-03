package pl.gieted.flappy_bird

import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event

@JsModule("p5")
@JsNonModule
@Suppress("ClassName")
open external class p5(sketch: (args: Any) -> Any, node: HTMLElement = definedExternally) {
    class Image {
        var height: Number
        var width: Number

        fun resize(width: Number, height: Number)
    }

    class SoundFile(path: String) {
        fun setVolume(volume: Number)

        fun play()
    }
    
    open fun setup()
    open fun draw()

    fun loadImage(
        path: String,
        successCallback: (p1: Image) -> Any = definedExternally,
        failureCallback: (p1: Event) -> Any = definedExternally
    ): Image
    
    var height: Number
    var width: Number

    fun background(image: Image): p5
    fun background(gray: Number): p5

    fun translate(x: Number, y: Number): p5

    fun rotate(angle: Number): p5

    fun millis(): Number

    fun scale(s: Number): p5

    var mouseIsPressed: Boolean

    var displayWidth: Number
    var displayHeight: Number

    fun createCanvas(w: Number, h: Number, renderer: String /* "p2d" | "webgl" */ = definedExternally): Any

    fun radians(degrees: Number): Number

    fun push()
    fun pop()
    
    fun noTint()
    fun noFill(): p5

    fun fill(v1: Number, v2: Number, v3: Number, alpha: Number = definedExternally): p5

    fun strokeWeight(weight: Number): p5

    fun stroke(gray: Number, alpha: Number = definedExternally): p5
    fun stroke(v1: Number, v2: Number, v3: Number): p5

    fun rect(x: Number, y: Number, w: Number, h: Number): p5

    fun tint(gray: Number, alpha: Number = definedExternally)

    fun image(img: Image, x: Number, y: Number)

    fun frameRate(): Number
}
