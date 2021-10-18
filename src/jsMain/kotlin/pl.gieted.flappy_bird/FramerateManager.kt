package pl.gieted.flappy_bird

import kotlinx.browser.document
import kotlinx.browser.window

class FramerateManager {

    private val framerateTag = document.getElementById("framerate")!!

    var currentFps: Int = 0

    fun start() {
        window.setInterval({
            framerateTag.textContent = "fps: $currentFps"
        }, 100)
    }
}
