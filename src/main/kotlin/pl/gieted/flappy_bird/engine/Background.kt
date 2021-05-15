package pl.gieted.flappy_bird.engine

import pl.gieted.flappy_bird.Renderer
import processing.core.PImage

class Background(renderer: Renderer, texture: PImage): Sprite(renderer, size = Vector2.screen, zIndex = -1000, texture = texture)
