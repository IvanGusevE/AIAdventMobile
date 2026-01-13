package ru.aiadvent.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.aiadvent.mobile.presentation.chat.ChatScreen
import ru.aiadvent.mobile.core.theme.AIAdventMobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIAdventMobileTheme {
                ChatScreen()
            }
        }
    }
}
