package ru.aiadvent.mobile.presentation.chat.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.aiadvent.mobile.domain.model.MistralModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatParametersDialog(
    prompt: String,
    temperature: Double,
    model: MistralModel,
    onDismiss: () -> Unit,
    onApply: (prompt: String, temperature: Double, model: MistralModel) -> Unit,
    modifier: Modifier = Modifier
) {
    var promptState by remember(prompt) { mutableStateOf(prompt) }
    var temperatureState by remember(temperature) { mutableFloatStateOf(temperature.toFloat()) }
    var modelState by remember(model) { mutableStateOf(model) }
    var modelMenuExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chat Parameters",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Column {
                Text(
                    text = "Configure the AI assistant's behavior:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = promptState,
                    onValueChange = { promptState = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    label = { Text("System Prompt") },
                    placeholder = { Text("Enter system prompt...") },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    trailingIcon = {
                        IconButton(onClick = { promptState = "" }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Clear"
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Model",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                ExposedDropdownMenuBox(
                    expanded = modelMenuExpanded,
                    onExpandedChange = { modelMenuExpanded = it }
                ) {
                    OutlinedTextField(
                        value = modelState.displayName,
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = modelMenuExpanded)
                        },
                        colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors()
                    )
                    ExposedDropdownMenu(
                        expanded = modelMenuExpanded,
                        onDismissRequest = { modelMenuExpanded = false }
                    ) {
                        MistralModel.entries.forEach { mistralModel ->
                            DropdownMenuItem(
                                text = { Text(mistralModel.displayName) },
                                onClick = {
                                    modelState = mistralModel
                                    modelMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Temperature",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Controls randomness: lower is more focused, higher is more creative",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = String.format("%.2f", temperatureState),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Slider(
                    value = temperatureState,
                    onValueChange = { temperatureState = it },
                    valueRange = 0.0f..2.0f,
                    steps = 19,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onApply(promptState, temperatureState.toDouble(), modelState)
                },
                enabled = promptState.trim().isNotEmpty()
            ) {
                Text("Apply")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
        modifier = modifier
    )
}
