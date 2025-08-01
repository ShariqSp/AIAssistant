package com.bharat_yatra.chat_bot_plugin.models

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    var isBotFirstMessage: Boolean ?= false
)
