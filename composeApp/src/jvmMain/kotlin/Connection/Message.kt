package Connection


class Message {

    constructor(message: String, orig: String = "admin", dest: String = "all") {
        this.message = message
        this.dest = dest
        this.orig = orig
    }

    override fun toString(): String {
        return "$orig: $message"
    }

    val message: String

    val orig: String

    val dest: String
}