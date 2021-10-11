package pl.gieted.flappy_bird.game

import pl.gieted.flappy_bird.engine.Image
import pl.gieted.flappy_bird.engine.Sound
import pl.gieted.flappy_bird.game.objects.Bird as BirdObject

data class Resources(
    val images: Images,
    val sounds: Sounds
) {

    data class Sounds(
        val hit: Sound,
        val wing: Sound,
        val point: Sound,
        val swoosh: Sound,
        val die: Sound
    )

    data class Images(
        val backgroundDay: Image,
        val backgroundNight: Image,
        val base: Image,
        val gameOver: Image,
        val message: Image,
        val greenPipe: Image,
        val redPipe: Image,
        val birds: Map<BirdObject.Color, Bird>,
        val digits: List<Image>,
    ) {
        data class Bird(
            val downFlap: Image,
            val midFlap: Image,
            val upFlap: Image
        )
    }
}
