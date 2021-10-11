package pl.gieted.flappy_bird.engine


class Background(renderer: Renderer, texture: Image) : Sprite(renderer, zIndex = -100, texture = texture)
