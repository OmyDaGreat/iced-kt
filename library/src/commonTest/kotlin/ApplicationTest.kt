package xyz.malefic.compose.iced

import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {
    // Simple counter model for testing
    data class Counter(
        val value: Int = 0,
    )

    sealed class CounterMessage {
        object Increment : CounterMessage()

        object Decrement : CounterMessage()
    }

    class CounterApp : Application<Counter, CounterMessage> {
        var lastDispatchedMessage: CounterMessage? = null

        override fun init(): Counter = Counter()

        override fun update(
            model: Counter,
            message: CounterMessage,
        ): Counter =
            when (message) {
                is CounterMessage.Increment -> model.copy(value = model.value + 1)
                is CounterMessage.Decrement -> model.copy(value = model.value - 1)
            }

        override fun view(model: Counter): Element {
            val dispatch: (CounterMessage) -> Unit = { msg ->
                lastDispatchedMessage = msg
            }

            return Column(
                children =
                    listOf(
                        Text("Count: ${model.value}"),
                        Button("Increment", onClick = { dispatch(CounterMessage.Increment) }),
                        Button("Decrement", onClick = { dispatch(CounterMessage.Decrement) }),
                    ),
            )
        }
    }

    @Test
    fun `test counter initialization`() {
        val app = CounterApp()
        val model = app.init()
        assertEquals(0, model.value)
    }

    @Test
    fun `test counter increment`() {
        val app = CounterApp()
        val model = Counter(value = 5)
        val newModel = app.update(model, CounterMessage.Increment)
        assertEquals(6, newModel.value)
    }

    @Test
    fun `test counter decrement`() {
        val app = CounterApp()
        val model = Counter(value = 5)
        val newModel = app.update(model, CounterMessage.Decrement)
        assertEquals(4, newModel.value)
    }

    @Test
    fun `test view generation`() {
        val app = CounterApp()
        val model = Counter(value = 42)
        val view = app.view(model)
        assertEquals(3, (view as Column).children.size)
    }

    @Test
    fun `test button click dispatches message`() {
        val app = CounterApp()
        val model = Counter(value = 0)
        val view = app.view(model) as Column
        val incrementButton = view.children[1] as Button

        // Simulate button click
        incrementButton.onClick()

        assertEquals(CounterMessage.Increment, app.lastDispatchedMessage)
    }
}
