package org.example.project.Server

import Connection.IOHandler
import Connection.Message
import Connection.User
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.net.ServerSocket
import java.net.Socket

class Server {
    private val users = mutableListOf<User>()
    val server = ServerSocket(1337)

    constructor(){
        println("server created")
    }
    suspend fun start() = coroutineScope {
        while (true){
            val socket = server.accept() //
            println("Arseniy connected: ${socket.inetAddress}")
            launch {
                client(socket)
            }
        }
    }

    private fun client(socket: Socket) {
        val handler = IOHandler(socket)

        val username = handler.read()!!
        users.add(User(username, socket))

        while (true) {
            sendToAllUsers(handler.read()!!, username)
        }
    }

    fun sendToAllUsers(message: String, username: String){
        for(user in users){
            val handler = IOHandler(user.getSocket(user.username)!!)
            handler.write(Message(message, User(username)))
            println("message: ${message}, to user:  ${user.username}")
        }
    }
}