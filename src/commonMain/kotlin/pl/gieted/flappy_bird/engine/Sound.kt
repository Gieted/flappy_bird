package pl.gieted.flappy_bird.engine

expect class Sound(processing: Processing, path: String) {
    fun amp(value: Float)
    
    fun play()
}
