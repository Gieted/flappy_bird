package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer
import processing.core.PImage
import processing.sound.SoundFile

open class ResourceLoader(private val renderer: Renderer) {
    protected open var totalTasks = 0
    private var completedTasks = 0

    val progressPercentage: Double
        get() = if (totalTasks > 0) completedTasks.toDouble() / totalTasks * 100 else 0.0


    fun loadImage(path: String): PImage {
        totalTasks++
        println("Loading image: $path")
        val image = renderer.loadImage(path)
        completedTasks++

        return image
    }

    fun loadSound(path: String): SoundFile {
        totalTasks++
        println("Loading sound: $path")
        val sound = SoundFile(renderer, path)
        completedTasks++
        return sound
    }
}
