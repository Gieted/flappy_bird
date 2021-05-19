package pl.gieted.flappy_bird.engine.collisions

import pl.gieted.flappy_bird.engine.collisions.colliders.Collider

interface Collidable {
    val collider: Collider
}
