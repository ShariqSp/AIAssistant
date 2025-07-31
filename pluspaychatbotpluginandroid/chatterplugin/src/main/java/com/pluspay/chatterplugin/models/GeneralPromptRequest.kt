package com.pluspay.chatterplugin.models

data class GeneralPromptRequest(
    val question: String? ="",
    val tenant : String ?= "pluspay"
)
