import java.nio.file.Path

interface ISubscriber {

    fun onChange(path: Path)
}