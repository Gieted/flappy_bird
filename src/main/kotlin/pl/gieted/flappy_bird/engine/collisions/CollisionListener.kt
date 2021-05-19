package pl.gieted.flappy_bird.engine.collisions

interface CollisionListener : Collidable {
    fun onCollision(hitObject: Collidable)
}
