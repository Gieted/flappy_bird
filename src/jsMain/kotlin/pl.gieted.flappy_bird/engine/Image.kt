package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.p5

actual class Image(val p5Image: p5.Image) {
    actual var height: Int
        get() {
            return p5Image.height.toInt()
        }
        set(value) {
            p5Image.height = value
        }
    
    actual var width: Int
        get() = p5Image.width.toInt()
        set(value) {
            p5Image.width = value
        }

    actual fun resize(x: Int, y: Int) {
        if (x == 200) {
            println("boo")
        }
        p5Image.resize(x, y)
    }
}
