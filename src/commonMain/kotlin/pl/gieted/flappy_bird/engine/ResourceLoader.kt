package pl.gieted.flappy_bird.engine

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

open class ResourceLoader(private val renderer: Renderer) {
    protected open var totalTasks = 0
    private var completedTasks = 0

    val progressPercentage: Double
        get() = if (totalTasks > 0) completedTasks.toDouble() / totalTasks * 100 else 0.0


    suspend fun loadImage(path: String): Image = coroutineScope {
        totalTasks++
        println("Loading image: $path")
        val image = async {
            renderer.loadImage("images/$path")
        }

        image.invokeOnCompletion { completedTasks++ }

        image.await()
    }

    fun loadSound(path: String): Sound {
        totalTasks++
        println("Loading sound: $path")

        val sound = Sound(renderer, "sounds/$path")
        sound.amp(Renderer.soundVolume.toFloat())
        completedTasks++

        return sound
    }
}
