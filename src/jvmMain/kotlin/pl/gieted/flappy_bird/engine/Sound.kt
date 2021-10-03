package pl.gieted.flappy_bird.engine

import processing.sound.SoundFile

actual class Sound actual constructor(processing: Processing, path: String) {
    private val soundFile = SoundFile(processing.pApplet, path)
    
    actual fun amp(value: Float) = soundFile.amp(value)
    
    actual fun play() = soundFile.play()
}
