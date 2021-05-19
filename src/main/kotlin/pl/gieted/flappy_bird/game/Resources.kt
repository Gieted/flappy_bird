package pl.gieted.flappy_bird.game

import processing.core.PImage
import processing.sound.SoundFile
import pl.gieted.flappy_bird.game.objects.Bird as BirdObject

data class Resources(
    val images: Images,
    val sounds: Sounds
) {

    data class Sounds(val hit: SoundFile, val wing: SoundFile, val point: SoundFile)

    data class Images(
        val backgroundDay: PImage,
        val backgroundNight: PImage,
        val base: PImage,
        val gameOver: PImage,
        val message: PImage,
        val greenPipe: PImage,
        val redPipe: PImage,
        val birds: Map<BirdObject.Color, Bird>,
        val digits: List<PImage>
    ) {
        data class Bird(
            val downFlap: PImage,
            val midFlap: PImage,
            val upFlap: PImage
        )
    }
}
