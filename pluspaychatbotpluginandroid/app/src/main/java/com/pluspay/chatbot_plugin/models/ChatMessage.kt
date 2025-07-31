package com.pluspay.chatbot_plugin.models

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    var isBotFirstMessage: Boolean ?= false
)
