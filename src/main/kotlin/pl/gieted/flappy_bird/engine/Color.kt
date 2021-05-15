package pl.gieted.flappy_bird.engine

data class Color(val r: Int, val g: Int, val b: Int) {
    companion object {
        val black = rgb(0, 0, 0)
        val white = rgb(255, 255, 255)
    }
}
