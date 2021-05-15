package pl.gieted.flappy_bird.scenes.objects

import pl.gieted.flappy_bird.Renderer
import pl.gieted.flappy_bird.engine.Sprite
import pl.gieted.flappy_bird.engine.Vector2
import pl.gieted.flappy_bird.scenes.FlappyBirdResources
import pl.gieted.flappy_bird.scenes.GameScene
import processing.sound.SoundFile

class Bird(
    renderer: Renderer,
    private val textures: FlappyBirdResources.Bird,
    private val onDeath: () -> Unit,
    private val swingSound: SoundFile
) : Sprite(renderer, Vector2(-xOffset, 50), texture = textures.downFlap) {
    private var timeCounter = 0

    private var standby = true
    private var standbyDirection = false
    private var velocity = Vector2.zero

    companion object {
        const val wingFlapInterval = 100
        const val xOffset = 150
        const val standbySpeed = 0.03
        const val flySpeed = 0.25
        const val gravityPower = 0.35
        const val swingPower = 8
        const val rotationSpeed = 2
        const val rotationUpperCap = -23.0
        const val rotationLowerCap = 90.0
    }

    private var flapDirection = false

    fun awake() {
        standby = false
    }

    private var mouseButtonLock = false

    private var targetRotation = 0.0

    private var isDead = false

    private fun kill() {
        isDead = true
        onDeath()
    }

    private fun swing() {
        velocity = Vector2(0, -swingPower)
        mouseButtonLock = true
        swingSound.play()
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            if (!isDead) {
                timeCounter += deltaTime
                if (timeCounter > wingFlapInterval) {
                    timeCounter = 0
                    texture = when (texture) {
                        textures.midFlap -> if (flapDirection.also {
                                flapDirection = !flapDirection
                            }) textures.upFlap else textures.downFlap
                        else -> textures.midFlap
                    }
                }
            } else {
                if (texture != textures.midFlap) {
                    texture = textures.midFlap
                }
            }

            if (standby) {
                if (standbyDirection) {
                    if (position.y >= 50) {
                        position -= Vector2(0, standbySpeed * deltaTime)
                    } else {
                        standbyDirection = !standbyDirection
                    }
                } else {
                    if (position.y <= 60) {
                        position += Vector2(0, standbySpeed * deltaTime)
                    } else {
                        standbyDirection = !standbyDirection
                    }
                }
            }

            if (!isDead) {
                position += Vector2(flySpeed * deltaTime, 0)
                position += velocity
                camera.position = Vector2(position.x + xOffset, camera.position.y)
            }


            if (!standby && !isDead) {
                velocity += Vector2(0, gravityPower * 20) * deltaTime
                if (mousePressed && !mouseButtonLock && isOnScreen) {
                    swing()
                } else if (!mousePressed) {
                    mouseButtonLock = false
                }

                targetRotation = velocity.y * 6
                if (targetRotation < rotationUpperCap) {
                    targetRotation = rotationUpperCap
                } else if (targetRotation > rotationLowerCap) {
                    targetRotation = rotationLowerCap
                }
                val rotationSmoothing = 30
                when {
                    rotation < targetRotation - rotationSmoothing -> {
                        rotation += rotationSpeed * deltaTime
                    }
                    rotation > targetRotation + rotationSmoothing -> {
                        rotation -= rotationSpeed * deltaTime
                    }
                    else -> {
                        rotation = targetRotation
                    }
                }
            }

            if (!isDead) {
                if (position.y > GameScene.groundLevel) {
                    position = Vector2(position.x, GameScene.groundLevel)
                    kill()
                }
            }
        }
    }
}
