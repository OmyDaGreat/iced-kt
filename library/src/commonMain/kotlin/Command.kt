package xyz.malefic.compose.iced

/**
 * Represents a command that produces messages asynchronously.
 * Commands are used to handle side effects in the Elm Architecture,
 * such as HTTP requests, timers, or other asynchronous operations.
 *
 * @param Message The type of message this command will produce
 */
interface Command<Message> {
    /**
     * Executes the command and invokes the callback with the resulting message.
     *
     * @param callback Function to call with the message when the command completes
     */
    suspend fun execute(callback: (Message) -> Unit)
}

/**
 * A command that does nothing and produces no messages.
 */
class NoneCommand<Message> : Command<Message> {
    override suspend fun execute(callback: (Message) -> Unit) {
        // No operation
    }
}

/**
 * Creates a batch of commands that will execute concurrently.
 *
 * @param commands The commands to batch together
 * @return A command that executes all provided commands
 */
fun <Message> batch(vararg commands: Command<Message>): Command<Message> =
    object : Command<Message> {
        override suspend fun execute(callback: (Message) -> Unit) {
            commands.forEach { it.execute(callback) }
        }
    }
