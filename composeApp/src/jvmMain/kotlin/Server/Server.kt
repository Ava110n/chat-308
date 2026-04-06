package Server

import java.net.ServerSocket

class Server {
    fun start() {
        val server = ServerSocket(1337)
        val client = server.accept()
        val reader = client.inputStream.bufferedReader()
        println(reader.readLine())

    }


}
