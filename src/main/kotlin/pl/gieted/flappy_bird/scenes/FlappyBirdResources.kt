package pl.gieted.flappy_bird.scenes

import pl.gieted.flappy_bird.scenes.objects.BirdColor
import processing.core.PImage
import processing.sound.SoundFile

data class FlappyBirdResources(
    val backgroundDay: PImage,
    val backgroundNight: PImage,
    val base: PImage,
    val gameOver: PImage,
    val message: PImage,
    val greenPipe: PImage,
    val redPipe: PImage,
    val birds: Map<BirdColor, Bird>,
    val numbers: List<PImage>,
    val sounds: Sounds
    ) {
    data class Bird(
        val downFlap: PImage,
        val midFlap: PImage,
        val upFlap: PImage
    )
    
    data class Sounds(val hit: SoundFile, val wing: SoundFile, val point: SoundFile)
}
