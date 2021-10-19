import kotlin.reflect.KClass
import kotlin.reflect.KFunction

@Suppress("UNCHECKED_CAST")
class HighScoreRepository {
    companion object {
        init {
            reload()
        }

        private lateinit var theClass: KClass<Any>
        private lateinit var constructor: KFunction<Any>

        fun reload() {
            theClass = DevClassLoader.loadClass("pl.gieted.flappy_bird.game.HighScoreRepository").kotlin as KClass<Any>
            constructor = theClass.constructors.first()
        }

        fun create() = constructor.call()
    }
}