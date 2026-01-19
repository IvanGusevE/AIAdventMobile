package ru.aiadvent.mobile.presentation.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.aiadvent.mobile.core.base.ObserveEffects
import ru.aiadvent.mobile.presentation.chat.components.ChatInput
import ru.aiadvent.mobile.presentation.chat.components.ChatParametersDialog
import ru.aiadvent.mobile.presentation.chat.components.MessageBubble
import ru.aiadvent.mobile.presentation.chat.components.PromptTypeMenu
import ru.aiadvent.mobile.presentation.chat.components.TypingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    val listState = rememberLazyListState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(state.messages.size) {
        if (state.messages.isNotEmpty()) {
            listState.animateScrollToItem(state.messages.size)
        }
    }

    ObserveEffects(viewModel.effects) { effect ->
        when (effect) {
            is Effect.ShowError -> {
                launch { snackbarHostState.showSnackbar(effect.message) }
            }
        }
    }

    state.paramsDialog?.let {
        ChatParametersDialog(
            prompt = it.systemPrompt,
            temperature = it.temperature,
            onDismiss = { viewModel.onEvent(Event.OnChatParamsDialogDismiss) },
            onApply = { prompt, temp ->
                viewModel.onEvent(Event.OnChatParamsDialogApply(prompt, temp))
            },
        )
    }

    Scaffold(
        modifier = modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "AI Chat",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(
                        onClick = { viewModel.onEvent(Event.OnChatParamsDialogOpen) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Custom System Prompt"
                        )
                    }

                    IconButton(
                        onClick = { viewModel.onEvent(Event.OnClearChatClick) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }

                    PromptTypeMenu(
                        selectedPromptType = state.selectedPromptType,
                        isExpanded = state.isPromptMenuExpanded,
                        onMenuClick = { viewModel.onEvent(Event.OnPromptMenuClick) },
                        onDismiss = { viewModel.onEvent(Event.OnPromptMenuDismiss) },
                        onPromptTypeSelected = { promptType ->
                            viewModel.onEvent(Event.OnPromptTypeSelected(promptType))
                        }
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier.imePadding()) {
                ChatInput(
                    text = state.inputText,
                    onTextChanged = { viewModel.onEvent(Event.OnInputChanged(it)) },
                    onSendClick = { viewModel.onEvent(Event.OnSendClick) },
                    enabled = !state.isLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (state.messages.isEmpty() && !state.isLoading) {
                EmptyState(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.messages,
                        key = { it.timestamp }
                    ) { message ->
                        MessageBubble(
                            message = message,
                            modifier = Modifier.animateItem()
                        )
                    }

                    if (state.isLoading) {
                        item { TypingIndicator() }
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "👋",
            style = MaterialTheme.typography.displayLarge
        )
        Text(
            text = "Start a conversation",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 16.dp)
        )
        Text(
            text = "Ask me anything!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
