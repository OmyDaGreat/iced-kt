package xyz.malefic.compose.iced

import kotlin.test.Test
import kotlin.test.assertEquals

class RuntimeTest {
    // Todo list model for testing
    data class TodoModel(
        val todos: List<String> = emptyList(),
        val input: String = "",
    )

    sealed class TodoMessage {
        data class UpdateInput(
            val text: String,
        ) : TodoMessage()

        object AddTodo : TodoMessage()

        data class RemoveTodo(
            val index: Int,
        ) : TodoMessage()
    }

    class TodoApp : Application<TodoModel, TodoMessage> {
        override fun init(): TodoModel = TodoModel()

        override fun update(
            model: TodoModel,
            message: TodoMessage,
        ): TodoModel =
            when (message) {
                is TodoMessage.UpdateInput -> model.copy(input = message.text)
                is TodoMessage.AddTodo ->
                    if (model.input.isNotBlank()) {
                        model.copy(
                            todos = model.todos + model.input,
                            input = "",
                        )
                    } else {
                        model
                    }
                is TodoMessage.RemoveTodo ->
                    model.copy(
                        todos = model.todos.filterIndexed { index, _ -> index != message.index },
                    )
            }

        override fun view(
            model: TodoModel,
            dispatch: (TodoMessage) -> Unit,
        ): Element =
            Column(
                children =
                    listOf(
                        Text("Todo List"),
                        TextField(
                            value = model.input,
                            onValueChange = { /* Would dispatch UpdateInput */ },
                            placeholder = "Enter a todo",
                        ),
                        Button("Add", onClick = { /* Would dispatch AddTodo */ }),
                    ) +
                        model.todos.mapIndexed { index, todo ->
                            Row(
                                children =
                                    listOf(
                                        Text(todo),
                                        Button("Remove", onClick = { /* Would dispatch RemoveTodo */ }),
                                    ),
                            )
                        },
            )
    }

    @Test
    fun `test todo initialization`() {
        val app = TodoApp()
        val model = app.init()
        assertEquals(0, model.todos.size)
        assertEquals("", model.input)
    }

    @Test
    fun `test update input`() {
        val app = TodoApp()
        val model = TodoModel()
        val newModel = app.update(model, TodoMessage.UpdateInput("Buy milk"))
        assertEquals("Buy milk", newModel.input)
    }

    @Test
    fun `test add todo`() {
        val app = TodoApp()
        val model = TodoModel(input = "Buy milk")
        val newModel = app.update(model, TodoMessage.AddTodo)
        assertEquals(1, newModel.todos.size)
        assertEquals("Buy milk", newModel.todos[0])
        assertEquals("", newModel.input)
    }

    @Test
    fun `test add todo with blank input does nothing`() {
        val app = TodoApp()
        val model = TodoModel(input = "  ")
        val newModel = app.update(model, TodoMessage.AddTodo)
        assertEquals(0, newModel.todos.size)
    }

    @Test
    fun `test remove todo`() {
        val app = TodoApp()
        val model = TodoModel(todos = listOf("Buy milk", "Buy eggs", "Buy bread"))
        val newModel = app.update(model, TodoMessage.RemoveTodo(1))
        assertEquals(2, newModel.todos.size)
        assertEquals("Buy milk", newModel.todos[0])
        assertEquals("Buy bread", newModel.todos[1])
    }

    @Test
    fun `test view generation with todos`() {
        val app = TodoApp()
        val model = TodoModel(todos = listOf("Todo 1", "Todo 2"))
        val view = app.view(model) {} as Column
        // Header text + TextField + Button + 2 todos
        assertEquals(5, view.children.size)
    }

    // Form model for testing various components
    data class FormModel(
        val name: String = "",
        val agreeToTerms: Boolean = false,
        val notifications: Boolean = false,
    )

    sealed class FormMessage {
        data class UpdateName(
            val name: String,
        ) : FormMessage()

        data class ToggleTerms(
            val checked: Boolean,
        ) : FormMessage()

        data class ToggleNotifications(
            val enabled: Boolean,
        ) : FormMessage()
    }

    class FormApp : Application<FormModel, FormMessage> {
        override fun init(): FormModel = FormModel()

        override fun update(
            model: FormModel,
            message: FormMessage,
        ): FormModel =
            when (message) {
                is FormMessage.UpdateName -> model.copy(name = message.name)
                is FormMessage.ToggleTerms -> model.copy(agreeToTerms = message.checked)
                is FormMessage.ToggleNotifications -> model.copy(notifications = message.enabled)
            }

        override fun view(
            model: FormModel,
            dispatch: (FormMessage) -> Unit,
        ): Element =
            Card(
                children =
                    listOf(
                        Text("Sign Up Form"),
                        Spacer(size = 16),
                        TextField(
                            value = model.name,
                            onValueChange = { /* dispatch */ },
                            placeholder = "Enter your name",
                        ),
                        Spacer(size = 8),
                        Checkbox(
                            checked = model.agreeToTerms,
                            onCheckedChange = { /* dispatch */ },
                            label = "I agree to terms",
                        ),
                        Switch(
                            checked = model.notifications,
                            onCheckedChange = { /* dispatch */ },
                            label = "Enable notifications",
                        ),
                    ),
            )
    }

    @Test
    fun `test form initialization`() {
        val app = FormApp()
        val model = app.init()
        assertEquals("", model.name)
        assertEquals(false, model.agreeToTerms)
        assertEquals(false, model.notifications)
    }

    @Test
    fun `test form update name`() {
        val app = FormApp()
        val model = FormModel()
        val newModel = app.update(model, FormMessage.UpdateName("John"))
        assertEquals("John", newModel.name)
    }

    @Test
    fun `test form toggle terms`() {
        val app = FormApp()
        val model = FormModel()
        val newModel = app.update(model, FormMessage.ToggleTerms(true))
        assertEquals(true, newModel.agreeToTerms)
    }

    @Test
    fun `test form view structure`() {
        val app = FormApp()
        val model = FormModel()
        val view = app.view(model) {} as Card
        // Text + Spacer + TextField + Spacer + Checkbox + Switch
        assertEquals(6, view.children.size)
    }
}
