import pl.gieted.flappy_bird.game.GameScene
import processing.core.PApplet
import processing.opengl.PJOGL.setIcon
import java.io.File
import java.net.URLClassLoader
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds.*
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible


const val classesUrl = "build\\classes\\kotlin\\jvm\\main"
const val resourcesUrl = "build\\processedResources\\jvm\\main"

object MainClassLoader : ClassLoader() {
    private class MyClassLoader : URLClassLoader(
        listOf(classesUrl, resourcesUrl).map { File(it).toURI().toURL() }.toTypedArray(), null
    ) {

        override fun loadClass(name: String?, resolve: Boolean): Class<*> = MainClassLoader.loadClass(name)

        fun actuallyLoad(name: String): Class<*> = super.loadClass(name, false)
    }

    private val staticClassLoader = MyClassLoader()
    private var preloadingLoader = MyClassLoader()
    private var gameSceneLoader = MyClassLoader()

    private val staticClasses = listOf(
        "pl.gieted.flappy_bird.engine.Renderer",
        "pl.gieted.flappy_bird.engine.Processing",
        "pl.gieted.flappy_bird.engine.Scene",
        "pl.gieted.flappy_bird.engine.LifecycleElement",
        
        "pl.gieted.flappy_bird.engine.Object",
        "pl.gieted.flappy_bird.engine.Vector2",
        "pl.gieted.flappy_bird.engine.Sound",
        "pl.gieted.flappy_bird.engine.Camera",
        "pl.gieted.flappy_bird.engine.Bounds",
    )

    private val preloadingClasses = listOf(
        "pl.gieted.flappy_bird.game.LoadingScene",
        "pl.gieted.flappy_bird.game.FlappyBirdResourceLoader",
        "pl.gieted.flappy_bird.game.Resources",
        "pl.gieted.flappy_bird.game.objects.Bird\$Color"
    )

    override fun loadClass(name: String, resolve: Boolean): Class<*> = when {
        staticClasses.any { name.startsWith(it) } -> staticClassLoader.actuallyLoad(name)
        preloadingClasses.any { name.startsWith(it) } -> preloadingLoader.actuallyLoad(name)
        name.startsWith("pl.gieted.flappy_bird") -> gameSceneLoader.actuallyLoad(name)
        else -> MainClassLoader::class.java.classLoader.loadClass(name)
    }

    fun newRenderer() {
        preloadingLoader = MyClassLoader()
    }

    fun newGameScene() {
        gameSceneLoader = MyClassLoader()
    }
}

fun main() {
    val fileWatcher = FileSystems.getDefault().newWatchService()
    Paths.get(classesUrl).register(fileWatcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)

    val rendererClass = MainClassLoader.loadClass("pl.gieted.flappy_bird.engine.Renderer").kotlin as KClass<Any>
    val rendererConstructor = rendererClass.constructors.first()

    val renderer = run {
        val setExtraSettings: Any.() -> Unit = {
            val pApplet = rendererClass.memberProperties.find { it.name == "pApplet" }!!.get(this)
            PApplet::getSurface.call(pApplet).apply {
                setTitle("Flappy Bird (dev)")
                setResizable(true)
            }
        }

        rendererConstructor.call(setExtraSettings)
    }
    
    val sceneProperty = rendererClass.memberProperties.find { it.name == "scene" }!! as KMutableProperty1<Any, Any>

    setIcon(*(1..5).map { "favicons/favicon-$it.png" }.toTypedArray())

    fun loadGameSceneClass() = MainClassLoader.loadClass("pl.gieted.flappy_bird.game.GameScene").kotlin as KClass<Any>

    var gameSceneClass: KClass<Any> = loadGameSceneClass()

    fun reloadRenderer() {
        
    }

    fun reloadGameScene() {
        MainClassLoader.newGameScene()
        val oldScene = sceneProperty.get(renderer)

        val resources =
            gameSceneClass.memberProperties.find { it.name == "resources" }!!.also { it.isAccessible = true }
                .get(oldScene)


        val highScore =
            gameSceneClass.memberProperties.find { it.name == "highScore" }!!.also { it.isAccessible = true }
                .get(oldScene)

        gameSceneClass = loadGameSceneClass()

        val gameScene = gameSceneClass.constructors.first().call(
            renderer,
            resources,
            highScore
        )

        sceneProperty.set(renderer, gameScene)
    }

    rendererClass.memberFunctions.find { it.name == "start" }!!.call(renderer)

    while (true) {
        val watchKey = fileWatcher.take()

        Thread.sleep(2000)
        watchKey.pollEvents()
        watchKey.reset()

        println("Reloading classes")
        if (sceneProperty.get(renderer)::class.simpleName!! == "GameScene") {
            reloadRenderer()
        }
    }
}
