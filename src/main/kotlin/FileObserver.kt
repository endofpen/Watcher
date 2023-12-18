import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchKey
import java.nio.file.WatchService

class FileObserver(override val subscribers: MutableSet<ISubscriber>) : IFileObserver {

    lateinit var pathToObserve: Path
    private val watchService: WatchService = FileSystems.getDefault().newWatchService()
    private lateinit var pathKey: WatchKey
    private lateinit var watchKey: WatchKey
    private var watchServiceStatus = false

    fun startObservation(path: String) {
        watchServiceStatus = true
        pathToObserve = Paths.get(path)
        pathKey = pathToObserve.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY)

        while (watchServiceStatus) {
            val watchKey = watchService.take()
            for (event in watchKey.pollEvents()) {
                if (event.context().toString().contains("src.txt")) notifiySubscriber(pathToObserve.resolve("src.txt"))
            }

            if (!watchKey.reset()) {
                watchKey.cancel()
                watchService.close()
                break
            }
        }

        pathKey.cancel()
    }

    fun stopObservation() {
        watchServiceStatus = false
        watchKey.cancel()
        pathKey.cancel()
        watchService.close()
    }

    override fun registerSubscriber(subscriber: ISubscriber) {
        subscribers.add(subscriber)
    }

    override fun unregisterSubscriber(subscriber: ISubscriber) {
        subscribers.remove(subscriber)
    }

    override fun notifiySubscriber(path: Path) {
        for (subscriber in subscribers) subscriber.onChange(path)
    }
}