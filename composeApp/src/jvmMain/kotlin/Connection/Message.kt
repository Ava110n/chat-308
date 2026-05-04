package Connection


class Message {

    constructor(message: String,
                orig: User = User("admin"),
                dest: User? = null) {
        this.message = message
        this.orig = orig
        this.dest = dest
    }

    override fun toString(): String {
        return "$orig: $message"
    }

    val message: String

    val orig: User

    val dest: User?
}