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
 * Represents a vertical container element that holds child elements.
 *
 * @property children The child elements to display
 * @property key Optional key for this element
 */
data class Column(
    val children: List<Element>,
    override val key: String? = null,
) : Element

/**
 * Represents a horizontal container element that holds child elements.
 *
 * @property children The child elements to display
 * @property key Optional key for this element
 */
data class Row(
    val children: List<Element>,
    override val key: String? = null,
) : Element

/**
 * Represents a button element that can trigger messages.
 *
 * @property label The text label for the button
 * @property onClick Callback invoked when button is clicked
 * @property key Optional key for this element
 */
data class Button(
    val label: String,
    val onClick: () -> Unit = {},
    override val key: String? = null,
) : Element

/**
 * Represents a text input field.
 *
 * @property value The current text value
 * @property onValueChange Callback invoked when text changes
 * @property placeholder Optional placeholder text
 * @property key Optional key for this element
 */
data class TextField(
    val value: String,
    val onValueChange: (String) -> Unit = {},
    val placeholder: String = "",
    override val key: String? = null,
) : Element

/**
 * Represents a checkbox element.
 *
 * @property checked Whether the checkbox is checked
 * @property onCheckedChange Callback invoked when checkbox state changes
 * @property label Optional label text
 * @property key Optional key for this element
 */
data class Checkbox(
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit = {},
    val label: String? = null,
    override val key: String? = null,
) : Element

/**
 * Represents a spacer element for adding space between elements.
 *
 * @property size The size of the spacer in dp
 * @property key Optional key for this element
 */
data class Spacer(
    val size: Int = 8,
    override val key: String? = null,
) : Element

/**
 * Represents a switch/toggle element.
 *
 * @property checked Whether the switch is on
 * @property onCheckedChange Callback invoked when switch state changes
 * @property label Optional label text
 * @property key Optional key for this element
 */
data class Switch(
    val checked: Boolean,
    val onCheckedChange: (Boolean) -> Unit = {},
    val label: String? = null,
    override val key: String? = null,
) : Element

/**
 * Represents a card container with elevation.
 *
 * @property children The child elements to display
 * @property key Optional key for this element
 */
data class Card(
    val children: List<Element>,
    override val key: String? = null,
) : Element
