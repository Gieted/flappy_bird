import javax.naming.OperationNotSupportedException
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties

class PAppletWrapper(val instance: Any) {
    private val processingProperty =
        instance::class.memberProperties.find { it.name == "processing" }!! as KMutableProperty1<Any, Any>

    var processing: Renderer
        get() = throw OperationNotSupportedException()
        set(value) {
            processingProperty.set(instance, value.instance)
        }
}
