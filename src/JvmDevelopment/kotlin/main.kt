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


fun main() {
    val classesUrl = "build\\classes\\kotlin\\jvm\\main"
    val fileWatcher = FileSystems.getDefault().newWatchService()
    Paths.get(classesUrl).register(fileWatcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)

    lateinit var gameSceneLoader: ClassLoader

    val mainClassLoader = object : URLClassLoader(arrayOf(File(classesUrl).toURI().toURL()), null) {

        val staticClasses = listOf(
            "pl.gieted.flappy_bird.engine.Renderer",
            "pl.gieted.flappy_bird.engine.Processing",
            "pl.gieted.flappy_bird.engine.Scene",
            "pl.gieted.flappy_bird.engine.LifecycleElement",
        )
        
        override fun findClass(name: String): Class<*>? {
            return when {
                staticClasses.any { name.startsWith(it) } -> super.findClass(name)
                name.startsWith("pl.gieted.flappy_bird") -> null
                else -> this::class.java.classLoader.loadClass(name)
            }
        }
    }

    fun createPreloadingLoader() = object : URLClassLoader(arrayOf(File(classesUrl).toURI().toURL()), mainClassLoader) {

        val preloadingClasses = listOf(
            "pl.gieted.flappy_bird.game.LoadingScene",
            "pl.gieted.flappy_bird.game.FlappyBirdResourceLoader",
            "pl.gieted.flappy_bird.game.Resources",
        )
        
        override fun findClass(name: String): Class<*>? {
            return when {
                preloadingClasses.any { name.startsWith(it) } -> super.findClass(name)
                else -> null
            }
        }
    }

    var preloadingLoader = createPreloadingLoader()

    fun createGameSceneLoader() = URLClassLoader(arrayOf(File(classesUrl).toURI().toURL()), preloadingLoader)

    gameSceneLoader = createGameSceneLoader()

    val rendererClass = gameSceneLoader.loadClass("pl.gieted.flappy_bird.engine.Renderer").kotlin as KClass<Any>
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

    fun reloadLoadingScene() {
    }

    fun reloadGameScene() {
        gameSceneLoader = createGameSceneLoader()
        val gameSceneClass = gameSceneLoader.loadClass("pl.gieted.flappy_bird.game.GameScene").kotlin as KClass<Any>
        val oldScene = sceneProperty.get(renderer)

        val resources =
            gameSceneClass.memberProperties.find { it.name == "resources" }!!.also { it.isAccessible = true }
                .get(oldScene)


        val highScore =
            gameSceneClass.memberProperties.find { it.name == "highScore" }!!.also { it.isAccessible = true }
                .get(oldScene)

        val gameScene = gameSceneClass.constructors.first().call(
            renderer,
            resources,
            highScore
        ) as GameScene

        sceneProperty.set(renderer, gameScene)
    }

    rendererClass.memberFunctions.find { it.name == "start" }!!.call(renderer)

    while (true) {
        val watchKey = fileWatcher.take()

        Thread.sleep(1000)
        watchKey.pollEvents()
        watchKey.reset()

        println("Reloading classes")
        if (sceneProperty.get(renderer)::class.simpleName!! == "GameScene") {
            reloadGameScene()
        }
    }
}
