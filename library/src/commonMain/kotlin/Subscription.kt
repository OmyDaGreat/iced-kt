package xyz.malefic.compose.iced

/**
 * Represents a subscription to external events.
 * Subscriptions allow applications to listen to events like time, keyboard input,
 * or other continuous streams of data.
 *
 * @param Message The type of message this subscription will produce
 */
interface Subscription<Message> {
    /**
     * Starts the subscription and invokes the callback whenever an event occurs.
     *
     * @param callback Function to call with messages when events occur
     * @return A function to call to unsubscribe/cleanup
     */
    suspend fun subscribe(callback: (Message) -> Unit): () -> Unit
}

/**
 * A subscription that produces no messages.
 */
class NoneSubscription<Message> : Subscription<Message> {
    override suspend fun subscribe(callback: (Message) -> Unit): () -> Unit {
        return {} // No-op cleanup
    }
}
