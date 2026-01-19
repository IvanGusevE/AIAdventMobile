package ru.aiadvent.mobile.presentation.chat.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.aiadvent.mobile.domain.prompt.PromptType

@Composable
fun PromptTypeMenu(
    selectedPromptType: PromptType,
    isExpanded: Boolean,
    onMenuClick: () -> Unit,
    onDismiss: () -> Unit,
    onPromptTypeSelected: (PromptType) -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onMenuClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.MoreVert,
            contentDescription = "Prompt Type Menu"
        )
    }

    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss
    ) {
        PromptType.entries.forEach { promptType ->
            DropdownMenuItem(
                text = {
                    Text(
                        text = promptType.toDisplayName(),
                        style = if (promptType == selectedPromptType) {
                            MaterialTheme.typography.bodyLarge.copy(
                                color = MaterialTheme.colorScheme.primary
                            )
                        } else {
                            MaterialTheme.typography.bodyLarge
                        }
                    )
                },
                onClick = {
                    onPromptTypeSelected(promptType)
                }
            )
        }
    }
}

private fun PromptType.toDisplayName(): String = when (this) {
    PromptType.QUESTIONNAIRE -> "Questionnaire"
    PromptType.JSON_STRUCTURED -> "JSON Structured"
    PromptType.GENERAL_ASSISTANT -> "General Assistant"
}
