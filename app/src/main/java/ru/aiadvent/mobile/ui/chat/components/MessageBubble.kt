package ru.aiadvent.mobile.ui.chat.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.aiadvent.mobile.data.model.MessageRole
import ru.aiadvent.mobile.ui.chat.ChatUiMessage

@Composable
fun MessageBubble(
    message: ChatUiMessage,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = when (message.role) {
            MessageRole.USER -> Arrangement.End
            MessageRole.ASSISTANT -> Arrangement.Start
            MessageRole.SYSTEM -> Arrangement.Center
        }
    ) {
        Column(
            modifier = Modifier.widthIn(max = 280.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        when (message.role) {
                            MessageRole.USER -> RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 20.dp,
                                bottomEnd = 4.dp
                            )

                            MessageRole.ASSISTANT -> RoundedCornerShape(
                                topStart = 20.dp,
                                topEnd = 20.dp,
                                bottomStart = 4.dp,
                                bottomEnd = 20.dp
                            )

                            MessageRole.SYSTEM -> RoundedCornerShape(20.dp)
                        }
                    )
                    .background(
                        when (message.role) {
                            MessageRole.USER -> MaterialTheme.colorScheme.primaryContainer
                            MessageRole.ASSISTANT -> MaterialTheme.colorScheme.secondaryContainer
                            MessageRole.SYSTEM -> MaterialTheme.colorScheme.tertiaryContainer
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = when (message.role) {
                        MessageRole.USER -> MaterialTheme.colorScheme.onPrimaryContainer
                        MessageRole.ASSISTANT -> MaterialTheme.colorScheme.onSecondaryContainer
                        MessageRole.SYSTEM -> MaterialTheme.colorScheme.onTertiaryContainer
                    }
                )
            }
        }
    }
}
