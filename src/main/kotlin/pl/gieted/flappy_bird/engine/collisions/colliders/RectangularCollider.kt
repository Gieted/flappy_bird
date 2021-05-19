package pl.gieted.flappy_bird.engine.collisions.colliders

import pl.gieted.flappy_bird.engine.Vector2

data class RectangularCollider(val position: Vector2, val size: Vector2) : Collider
