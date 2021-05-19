package pl.gieted.flappy_bird.engine.collisions.colliders

data class MultiCollider(val subColliders: Set<Collider>) : Collider {
}
