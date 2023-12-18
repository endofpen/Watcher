import java.nio.file.Path

interface IFileObserver {

    val subscribers: MutableSet<ISubscriber>

    fun registerSubscriber(subscriber: ISubscriber)

    fun unregisterSubscriber(subscriber: ISubscriber)

    fun notifiySubscriber(path: Path)
}