package Connection

import java.net.Socket

class User {
    constructor(username: String, socket: Socket) {
        this.username = username
        this.socket = socket
    }
    constructor(username: String){
        this.username = username
        this.socket = null
    }

    fun getSocket(username: String): Socket? {
        return socket
    }
    val username: String
    private val socket: Socket?

}