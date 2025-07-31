package com.pluspay.chatterplugin.models

data class ChatMessage(
    val text: String,
    val isUser: Boolean,
    var isBotFirstMessage: Boolean ?= false
)
