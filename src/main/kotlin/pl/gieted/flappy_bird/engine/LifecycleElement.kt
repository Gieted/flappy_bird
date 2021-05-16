package pl.gieted.flappy_bird.engine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import pl.gieted.flappy_bird.Renderer

abstract class LifecycleElement(protected val renderer: Renderer) {
    protected val lifecycleScope = CoroutineScope(Dispatchers.Default)

    private val calledFunctions = mutableSetOf<String>()

    fun once(functionId: String, function: () -> Unit) {
        if (functionId !in calledFunctions) {
            function()
            calledFunctions.add(functionId)
        }
    }

    abstract fun setup()
    
    abstract fun draw()
    
    open fun destroy() {
        lifecycleScope.cancel()
    }
}
