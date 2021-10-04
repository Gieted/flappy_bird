package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.p5

actual class Sound actual constructor(processing: Processing, path: String) {
    private val soundFile = p5.SoundFile(path)
    
    actual fun amp(value: Float) = soundFile.setVolume(value)

    actual fun play() = soundFile.play()
}
