package com.pluspay.chatbot_plugin.models

data class AppGuideStepsRequest(
    val question: String? = "",
    val tenant: String = "pluspay"
)
