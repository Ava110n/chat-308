package org.example.project.Server

import Connection.IOHandler
import java.net.ServerSocket

class Server {
    fun start() {
        val server = ServerSocket(1337)
        val client = server.accept()
        println(client.port)
        println(client)


        val handler = IOHandler(client)

        println("server created")

        while (true) {

            handler.read()

        }
    }


}