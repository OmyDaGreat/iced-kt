@file:Suppress("ComposableNaming")

package xyz.malefic.compose.iced

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Runtime for executing Iced applications with Jetpack Compose.
 * This provides the bridge between the Elm Architecture model and Compose UI.
 *
 * @param Model The application's state type
 * @param Message The type of messages that can update the state
 */
class IcedRuntime<Model, Message>(
    private val application: Application<Model, Message>,
) {
    /**
     * Runs the application and returns a Composable function.
     * This is the main entry point for rendering an Iced application.
     */
    @Composable
    fun run() {
        var model by remember { mutableStateOf(application.init()) }

        val dispatch: (Message) -> Unit = { message ->
            model = application.update(model, message)
        }

        MaterialTheme {
            renderElement(application.view(model, dispatch), dispatch)
        }
    }

    @Composable
    private fun renderElement(
        element: Any,
        dispatch: (Message) -> Unit,
    ) {
        when (element) {
            is Text -> Text(text = element.content)

            is Column ->
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    element.children.forEach { child ->
                        renderElement(child, dispatch)
                    }
                }

            is Row ->
                Row(
                    modifier = Modifier.padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    element.children.forEach { child ->
                        renderElement(child, dispatch)
                    }
                }

            is Button ->
                Button(onClick = element.onClick) {
                    Text(element.label)
                }

            is TextField ->
                OutlinedTextField(
                    value = element.value,
                    onValueChange = element.onValueChange,
                    placeholder =
                        if (element.placeholder.isNotEmpty()) {
                            { Text(element.placeholder) }
                        } else {
                            null
                        },
                    modifier = Modifier.fillMaxWidth(),
                )

            is Checkbox ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Checkbox(
                        checked = element.checked,
                        onCheckedChange = element.onCheckedChange,
                    )
                    element.label?.let { label ->
                        Text(text = label)
                    }
                }

            is Spacer -> Spacer(modifier = Modifier.height(element.size.dp).width(element.size.dp))

            is Switch ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    element.label?.let { label ->
                        Text(text = label)
                    }
                    Switch(
                        checked = element.checked,
                        onCheckedChange = element.onCheckedChange,
                    )
                }

            is Card ->
                Card(
                    modifier = Modifier.padding(8.dp),
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        element.children.forEach { child ->
                            renderElement(child, dispatch)
                        }
                    }
                }
        }
    }
}

/**
 * Creates and runs an Iced application inside a Jetpack Compose context.
 *
 * This extension constructs an [IcedRuntime] for the receiving [Application]
 * and immediately invokes its composable `run` function. Use this to render
 * an Iced-style Elm architecture app with Compose, wiring the application's
 * `init`, `update`, and `view` implementations into the Compose runtime.
 *
 * @receiver The [Application] instance to run.
 * @typeParam Model The application's state type.
 * @typeParam Message The type of messages that can update the state.
 */
@Composable
fun <Model, Message> Application<Model, Message>.runIced() {
    IcedRuntime(this).run()
}

/**
 * Convenience operator function that runs this [Application] as a Composable.
 *
 * This delegates to [runIced] so the application can be invoked with
 * `myApplication()` where `myApplication` is an `Application<Model, Message>`.
 *
 * @param Model The application's state type.
 * @param Message The type of messages that can update the state.
 * @receiver The [Application] instance to run.
 */
@Composable
operator fun <Model, Message> Application<Model, Message>.invoke() = runIced()
