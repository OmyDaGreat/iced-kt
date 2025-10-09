package xyz.malefic.compose.iced

/**
 * Core interface for an Iced application following the Elm Architecture.
 *
 * This interface defines the contract for building reactive applications where
 * state changes are driven by messages and rendered through a view function.
 *
 * @param Model The application's state type
 * @param Message The type of messages that can update the state
 */
interface Application<Model, Message> {
    /**
     * The initial state of the application.
     */
    fun init(): Model

    /**
     * Pure function that updates the model based on a message.
     *
     * @param model The current state
     * @param message The message to process
     * @return The new state after processing the message
     */
    fun update(
        model: Model,
        message: Message,
    ): Model

    /**
     * Describes how the current model should be rendered.
     * This function will be platform-specific and typically returns
     * a composable description of the UI.
     *
     * @param model The current state to render
     * @return A description of the view (platform-specific)
     */
    fun view(model: Model): Any
}
