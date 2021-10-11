import java.io.File
import java.net.URLClassLoader

object DevClassLoader : ClassLoader() {
    private class MyClassLoader : URLClassLoader(
        listOf(classesUrl, resourcesUrl).map { File(it).toURI().toURL() }.toTypedArray(), null
    ) {

        override fun loadClass(name: String?, resolve: Boolean): Class<*> = DevClassLoader.loadClass(name)

        fun actuallyLoad(name: String): Class<*> = super.loadClass(name, false)
    }

    private val staticLoader = MyClassLoader()
    private var rendererLoader = MyClassLoader()
    private var resourcesLoader = MyClassLoader()

    private val staticClasses = listOf(
        "pl.gieted.flappy_bird.engine.Processing",
        "pl.gieted.flappy_bird.engine.PAppletWrapper"
    )

    private val resourcesClasses = listOf(
        "pl.gieted.flappy_bird.game.objects.Bird\$Color",
//        "pl.gieted.flappy_bird.game.LoadingScene",
//        "pl.gieted.flappy_bird.game.FlappyBirdResourceLoader",
        "pl.gieted.flappy_bird.game.Resources",
        "pl.gieted.flappy_bird.engine.Sound",
        "pl.gieted.flappy_bird.engine.Image",
    )

    override fun loadClass(name: String, resolve: Boolean): Class<*> = when {
        staticClasses.any { name.startsWith(it) } -> staticLoader.actuallyLoad(name)
        resourcesClasses.any { name.startsWith(it) } -> resourcesLoader.actuallyLoad(name)
        name.startsWith("pl.gieted.flappy_bird") -> rendererLoader.actuallyLoad(name)
        else -> DevClassLoader::class.java.classLoader.loadClass(name)
    }

    fun newRenderer() {
        rendererLoader = MyClassLoader()
    }

    fun newResources() {
        newRenderer()
        resourcesLoader = MyClassLoader()
    }
}
