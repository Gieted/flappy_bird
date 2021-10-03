package pl.gieted.flappy_bird.engine


class Background(renderer: Renderer, private val texture: Image) : Object(renderer, zIndex = -100) {
    
    override fun setup() {
    }

    override fun draw() {
        super.draw()
        with(renderer) {
            if (texture.width == width && texture.height == height) {
                background(texture)
            }
        }
    }
}
