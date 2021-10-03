package pl.gieted.flappy_bird.engine.collisions.colliders

import pl.gieted.flappy_bird.engine.Vector2

data class CircularCollider(val position: Vector2, val diameter: Double): Collider
