package pl.gieted.flappy_bird.game.objects

import pl.gieted.flappy_bird.engine.*
import pl.gieted.flappy_bird.engine.collisions.Collidable
import pl.gieted.flappy_bird.engine.collisions.CollisionListener
import pl.gieted.flappy_bird.engine.collisions.colliders.CircularCollider
import pl.gieted.flappy_bird.engine.collisions.colliders.Collider
import pl.gieted.flappy_bird.game.Resources
import kotlin.math.pow

class Bird(
    renderer: Renderer,
    private val textures: Resources.Images.Bird,
    private val onDeath: () -> Unit,
    private val swingSound: Sound,
    private val fallSound: Sound
) : Sprite(renderer, Vector2(0, autopilotHeight), texture = textures.downFlap, scale = 1.47), CollisionListener {

    companion object {
        const val wingFlapInterval = 100
        const val xOffset = -150
        const val autopilotHeight = -35
        const val autopilotRange = 5
        const val autopilotSpeed = 0.03
        const val gravityPower = 0.03
        const val swingPower = 10.5
        const val flySpeed = 0.32
        const val velocityCap = 12.0
        const val hitBoxSize = 30.0
        const val minRotation = -25.0
        const val maxRotation = 90.0
    }

    enum class Color {
        Blue, Red, Yellow
    }

    private var flapCounter = 0

    private var yVelocity: Double = 0.0
        set(value) {
            field = limit(value, -velocityCap)
        }

    private var flapDirection = false
    private var mouseButtonLock = false
    private var autopilot = true
    private var autopilotDirection = 1
    private var startXPos: Double? = null

    var isAlive = true
        private set

    val distanceFlown: Double
        get() {
            val startXPos = startXPos
            return if (startXPos != null) position.x - startXPos else 0.0
        }

    fun kill() {
        isAlive = false
        onDeath()
        zIndex = 4
    }

    fun swing() {
        if (autopilot) {
            autopilot = false
            startXPos = position.x
        }
        mouseButtonLock = true
        swingSound.play()
        yVelocity = swingPower
    }

    private var targetRotation: Double = rotation

    override fun draw() {
        super.draw()
        with(renderer) {
            if (isAlive) {
                flapCounter += deltaTime
                if (flapCounter > wingFlapInterval) {
                    flapCounter = 0
                    texture = when (texture) {
                        textures.midFlap -> if (flapDirection.also {
                                flapDirection = !flapDirection
                            }) textures.upFlap else textures.downFlap
                        else -> textures.midFlap
                    }
                }

                position += Vector2((flySpeed * deltaTime).toInt(), 0)
            } else {
                if (texture != textures.midFlap) {
                    texture = textures.midFlap
                }
            }

            if (autopilot) {
                val autopilotMaxHeight = autopilotHeight + autopilotRange
                val autopilotMinHeight = autopilotHeight - autopilotRange
                if (position.y >= autopilotMaxHeight && autopilotDirection == 1) autopilotDirection = -1
                if (position.y <= autopilotMinHeight && autopilotDirection == -1) autopilotDirection = 1
                position = Vector2(
                    position.x,
                    limit(
                        position.y + autopilotSpeed * deltaTime * autopilotDirection,
                        autopilotMinHeight.toDouble(),
                        autopilotMaxHeight.toDouble()
                    )
                )
            } else {
                yVelocity -= gravityPower * deltaTime

                targetRotation = -(yVelocity.pow(3) / 15.7 + 20)

                rotation = limit(
                    when {
                        targetRotation > rotation -> limit(
                            rotation + (if (isAlive) 0.27 else 0.4) * deltaTime,
                            upperBound = targetRotation
                        )
                        targetRotation < rotation -> limit(
                            rotation - 0.4 * deltaTime,
                            lowerBound = targetRotation
                        )
                        else -> rotation
                    }, minRotation, maxRotation
                )
            }

            position += Vector2(0, yVelocity * (deltaTime / 16.0))
        }
    }

    override fun onCollision(hitObject: Collidable) {
        if (isAlive) {
            kill()
            fallSound.play()
        }
    }

    override val collider: Collider
        get() = CircularCollider(position, hitBoxSize)
}
