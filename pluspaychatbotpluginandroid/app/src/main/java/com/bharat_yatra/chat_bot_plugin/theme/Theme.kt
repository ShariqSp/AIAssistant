package com.bharat_yatra.chat_bot_plugin.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


data class ChatterColors(
    val background: Color,
    val userBubble: Color,
    val botBubble: Color,
    val userText: Color,
    val botText: Color,
    val inputField: Color,
    val sendButton: Color,
    val sendButtonText: Color,
    val clickableBox: Color
)

val LightChatterColors = ChatterColors(
    background = LightBackground,
    userBubble = LightUserBubble,
    botBubble = LightBotBubble,
    userText = LightUserText,
    botText = LightBotText,
    inputField = LightInputField,
    sendButton = LightSendButton,
    sendButtonText = LightSendButtonText,
    clickableBox = LightClickableBox
)

val DarkChatterColors = ChatterColors(
    background = DarkBackground,
    userBubble = DarkUserBubble,
    botBubble = DarkBotBubble,
    userText = DarkUserText,
    botText = DarkBotText,
    inputField = DarkInputField,
    sendButton = DarkSendButton,
    sendButtonText = DarkSendButtonText,
    clickableBox = DarkClickableBox
)

@Composable
fun provideChatterColors(): ChatterColors =
    if (isSystemInDarkTheme()) DarkChatterColors else LightChatterColors

@Composable
fun PluspaychatbotpluginandroidTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
//    val colorScheme = when {
//        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
//            val context = LocalContext.current
//            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
//        }
//
//        darkTheme -> DarkColorScheme
//        else -> LightColorScheme
//    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}