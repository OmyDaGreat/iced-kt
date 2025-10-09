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
     * This function returns a tree of Elements that describe the UI.
     *
     * @param model The current state to render
     * @return A description of the view as an Element tree
     */
    fun view(model: Model): Element
}
