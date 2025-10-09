package xyz.malefic.compose.iced

/**
 * Base interface for UI elements in the Iced framework.
 * This provides a common abstraction for platform-specific UI implementations.
 */
interface Element {
    /**
     * A unique identifier for this element, used for efficient updates.
     */
    val key: String?
}

/**
 * Represents a simple text element.
 *
 * @property content The text content to display
 * @property key Optional key for this element
 */
data class Text(
    val content: String,
    override val key: String? = null,
) : Element

/**
 * Represents a container element that holds child elements.
 *
 * @property children The child elements to display
 * @property key Optional key for this element
 */
data class Column(
    val children: List<Element>,
    override val key: String? = null,
) : Element

/**
 * Represents a button element that can trigger messages.
 *
 * @property label The text label for the button
 * @property key Optional key for this element
 */
data class Button(
    val label: String,
    override val key: String? = null,
) : Element
