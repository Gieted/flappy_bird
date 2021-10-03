package pl.gieted.flappy_bird.engine

expect class Image {
    var height: Int
    var width: Int
    
    fun resize(x: Int, y: Int )
}
