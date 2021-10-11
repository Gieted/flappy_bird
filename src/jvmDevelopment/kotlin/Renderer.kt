import processing.core.PApplet
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.jvmErasure

@Suppress("UNCHECKED_CAST")
class Renderer(val instance: Any) {
    companion object {
        init {
            reload()
        }

        private lateinit var theClass: KClass<Any>
        private lateinit var constructor: KFunction<Any>

        fun reload() {
            theClass = DevClassLoader.loadClass("pl.gieted.flappy_bird.engine.Renderer").kotlin as KClass<Any>
            constructor = theClass.constructors.first()
        }

        private val setExtraSettings: Any.() -> Unit = {
            val renderer = Renderer(this)
            val pApplet = renderer.pApplet.instance as PApplet
            pApplet.surface.apply {
                setTitle("Flappy Bird (dev)")
                setResizable(true)
            }
        }

        fun create() = Renderer(constructor.call(setExtraSettings))
    }

    private val sceneProperty =
        instance::class.memberProperties.find { it.name == "scene" }!! as KMutableProperty1<Any, Any>
    
    private val pAppletProperty =
        instance::class.memberProperties.find { it.name == "pApplet" }!! as KProperty1<Any, Any>
    
    private val startFunc = instance::class.memberFunctions.find { it.name == "start" }!!
    private val setupFunc = instance::class.memberFunctions.find { it.name == "setup" }!!
    private val settingsFunc = instance::class.memberFunctions.find { it.name == "settings" }!!

    var scene: GameScene
        get() = GameScene(sceneProperty.get(instance))
        set(value) {
            sceneProperty.set(instance, value.instance)
        }

    var pApplet: PAppletWrapper
        get() = PAppletWrapper(pAppletProperty.get(instance))
        set(value) {
            pAppletProperty.javaField!!.also { it.isAccessible = true }.set(instance, value.instance)
        }

    fun start() = startFunc.call(instance)

    fun setup() = setupFunc.call(instance)
    
    fun settings() = settingsFunc.call(instance)
}
