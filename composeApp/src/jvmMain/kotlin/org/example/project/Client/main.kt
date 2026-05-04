import org.example.project.Client.chat
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import java.net.Socket


fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Chat",
    ) {
        val socket = Socket("127.0.0.1",1337)
        chat(socket)
    }
}//67