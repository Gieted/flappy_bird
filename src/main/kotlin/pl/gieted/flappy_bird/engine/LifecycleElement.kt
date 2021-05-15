package pl.gieted.flappy_bird.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import pl.gieted.flappy_bird.Renderer

abstract class LifecycleElement(protected val renderer: Renderer) {
    protected val lifecycleScope = CoroutineScope(Dispatchers.Default)

    abstract fun setup()
    
    abstract fun draw()
    
    open fun destroy() {
        lifecycleScope.cancel()
    }
}
