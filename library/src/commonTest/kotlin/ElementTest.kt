package xyz.malefic.compose.iced

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ElementTest {
    @Test
    fun `test text element creation`() {
        val text = Text("Hello")
        assertEquals("Hello", text.content)
        assertNull(text.key)
    }

    @Test
    fun `test text element with key`() {
        val text = Text("Hello", key = "greeting")
        assertEquals("Hello", text.content)
        assertEquals("greeting", text.key)
    }

    @Test
    fun `test column element creation`() {
        val column =
            Column(
                children =
                    listOf(
                        Text("Item 1"),
                        Text("Item 2"),
                    ),
            )
        assertEquals(2, column.children.size)
        assertNull(column.key)
    }

    @Test
    fun `test button element creation`() {
        val button = Button("Click me")
        assertEquals("Click me", button.label)
        assertNull(button.key)
    }
}
