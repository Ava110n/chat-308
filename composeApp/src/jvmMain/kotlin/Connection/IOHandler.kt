package Connection

import java.io.BufferedReader
import java.io.BufferedWriter
import java.net.Socket

class IOHandler(socket: Socket) {
    private val reader: BufferedReader = socket.getInputStream().bufferedReader()
    private val writer: BufferedWriter = socket.getOutputStream().bufferedWriter()

    fun write(message: Message) {
            writer.write(message.message)
            writer.newLine()
            writer.flush()

    }

    fun read(): String? {
            val line = reader.readLine()


            return line
    }
}
