import processing.opengl.PJOGL.setIcon
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds.*


const val classesUrl = "build\\classes\\kotlin\\jvm\\main"
const val resourcesUrl = "build\\processedResources\\jvm\\main"

fun main() {
    val fileWatcher = FileSystems.getDefault().newWatchService()
    Paths.get(classesUrl).register(fileWatcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)

    var renderer = Renderer.create()

    setIcon(*(1..5).map { "favicons/favicon-$it.png" }.toTypedArray())

    fun reloadRenderer(preserveResources: Boolean) {
        var resources: Any? = null
        var highScore: Int? = null

        if (preserveResources) {
            resources = renderer.scene.resources
            highScore = renderer.scene.highScore
        }

        DevClassLoader.newRenderer()

        val pAppletWrapper = renderer.pApplet
        
        Renderer.reload()
        renderer = Renderer.create()
        
        renderer.pApplet = pAppletWrapper
        pAppletWrapper.processing = renderer
        renderer.settings()
        renderer.setup()
        
        if (preserveResources) {
            GameScene.reload()
            val gameScene = GameScene.create(renderer, resources!!, highScore!!)

            renderer.scene = gameScene
        }
    }

    renderer.start()

    while (true) {
        val watchKey = fileWatcher.take()

        Thread.sleep(2000)
        watchKey.pollEvents()
        watchKey.reset()

        println("Reloading classes")
        reloadRenderer("GameScene" in renderer.scene.instance::class.simpleName!!)
    }
}
