package ru.aiadvent.mobile.presentation.chat.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.aiadvent.mobile.domain.model.ChatInteraction
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ChatInteractionsDialog(
    interactions: List<ChatInteraction>,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Chat Interactions",
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            if (interactions.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No interactions yet",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = interactions,
                        key = { it.created }
                    ) { interaction ->
                        InteractionItem(interaction = interaction)
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        modifier = modifier
    )
}

@Composable
private fun InteractionItem(
    interaction: ChatInteraction,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatResponseTime(interaction.responseTimeMs),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = interaction.model,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TokenInfo(
                    label = "Prompt",
                    value = interaction.promptTokens
                )
                TokenInfo(
                    label = "Completion",
                    value = interaction.completionTokens
                )
                TokenInfo(
                    label = "Total",
                    value = interaction.totalTokens,
                    highlight = true
                )
            }
        }
    }
}

@Composable
private fun TokenInfo(
    label: String,
    value: Int,
    highlight: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value.toString(),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = if (highlight) FontWeight.Bold else FontWeight.Normal,
            color = if (highlight) {
                MaterialTheme.colorScheme.primary
            } else {
                MaterialTheme.colorScheme.onSurface
            }
        )
    }
}

private fun formatTimestamp(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, HH:mm:ss", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}

private fun formatResponseTime(responseTimeMs: Long): String {
    return when {
        responseTimeMs < 1000 -> "${responseTimeMs}ms"
        responseTimeMs < 60000 -> String.format("%.2fs", responseTimeMs / 1000.0)
        else -> String.format("%.2fm", responseTimeMs / 60000.0)
    }
}
