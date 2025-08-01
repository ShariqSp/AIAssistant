package com.bharat_yatra.chat_bot_plugin.models

data class GeneralPromptRequest(
    val question: String? ="",
    val tenant : String ?= "bharath yathra"
)
