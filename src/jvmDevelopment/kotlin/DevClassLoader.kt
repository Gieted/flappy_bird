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
        "pl.gieted.flappy_bird.game.Resources",
        "pl.gieted.flappy_bird.engine.Sound",
        "pl.gieted.flappy_bird.engine.Image",
    )

    override fun loadClass(name: String, resolve: Boolean): Class<*> {
        return when {
            name == "pl.gieted.flappy_bird.engine.Image" -> throw ClassNotFoundException()
            name.startsWith("pl.gieted.flappy_bird") -> try {
                when {
                    staticClasses.any { name.startsWith(it) } -> staticLoader.actuallyLoad(name)
                    resourcesClasses.any { name.startsWith(it) } -> resourcesLoader.actuallyLoad(name)
                    else -> rendererLoader.actuallyLoad(name)
                }
            } catch (exception: ClassNotFoundException) {
                println("Waiting for classes...")
                Thread.sleep(1000)
                return loadClass(name, resolve)
            }
            else -> DevClassLoader::class.java.classLoader.loadClass(name)
        }

    }

    fun newRenderer() {
        rendererLoader = MyClassLoader()
    }
}
