package com.pluspay.chatbot_plugin.models

data class GeneralPromptRequest(
    val question: String? ="",
    val tenant : String ?= "pluspay"
)
