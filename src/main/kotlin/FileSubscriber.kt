import java.io.File
import java.nio.file.Path

class FileSubscriber : ISubscriber {

    override fun onChange(path: Path) {
        readAlleLinesFromFile(path).forEach { print(it) }
    }

    private fun readAlleLinesFromFile(path: Path): List<String> = File(path.toUri()).useLines { it.toList() }
}