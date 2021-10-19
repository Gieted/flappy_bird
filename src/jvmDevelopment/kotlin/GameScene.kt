import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

@Suppress("UNCHECKED_CAST")
class GameScene(val instance: Any) {
    companion object {
        init {
            reload()
        }

        private lateinit var theClass: KClass<Any>
        private lateinit var constructor: KFunction<Any>

        fun reload() {
            theClass = DevClassLoader.loadClass("pl.gieted.flappy_bird.game.GameScene").kotlin as KClass<Any>
            constructor = theClass.constructors.first()
        }

        fun create(renderer: Renderer, resources: Any, highScore: Int) =
            GameScene(constructor.call(renderer.instance, resources, highScore, HighScoreRepository.create()))
    }

    private val resourcesProperty = instance::class.memberProperties.find { it.name == "resources" }!!
        .also { it.isAccessible = true } as KProperty1<Any, Any>
    
    private val highScoreProperty = instance::class.memberProperties.find { it.name == "highScore" }!!
        .also { it.isAccessible = true } as KProperty1<Any, Any>

    val resources: Any
        get() = resourcesProperty.get(instance)

    val highScore: Int
        get() = highScoreProperty.get(instance) as Int
}
