package Connection

import java.net.Socket

class IOHandler {
    val client = Socket("127.0.0.1", 1337)

    fun write(message: String) {

        client.outputStream.write(message.toByteArray())

    }
    fun read() {

        client.getInputStream().read("Hello!".toByteArray())

    }

//    client.close()
}