package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.Renderer
import pl.gieted.flappy_bird.engine.Object
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2

class ScrollingElement(
    renderer: Renderer,
    private val elementFactory: () -> Sprite,
    private val parallax: Double = 0.0,
    private val startXPos: Double = renderer.camera.bounds.left - Vector2.defaultSize.x * 5,
    zIndex: Int = 0,
    private val offset: Double = -1.0
) : Object(renderer, zIndex = zIndex) {

    private val elements = mutableListOf<Sprite>()

    private fun spawnNext() {
        val element = elementFactory()
        val xPos = if (elements.isEmpty()) startXPos else elements.last().position.x + element.texture.width + offset
        element.position = Vector2(xPos, element.position.y)
        addChild(element)
        elements.add(element)
    }

    private fun removeElement(element: Sprite) {
        elements.remove(element)
        removeChild(element)
    }

    override fun setup() {
        do {
            spawnNext()
        } while (elements.last().bounds.left < renderer.camera.bounds.right)
        spawnNext()
    }


    private var lastCameraXPos = 0.0

    override fun draw() {
        with(renderer) {
            for (element in elements) {
                if (element.bounds.right < camera.bounds.left) {
                    removeElement(element)
                    spawnNext()
                    break
                }
            }

            elements.forEach { it.position -= Vector2((parallax + 1) * (camera.position.x - lastCameraXPos), 0) }
            lastCameraXPos = camera.position.x
        }
        super.draw()
    }
}
