package com.bharat_yatra.chat_bot_plugin.models

data class ChatterRequest(
    val prompt: String? = "",
    val tenant : String ?= "bharath yathra"
)
