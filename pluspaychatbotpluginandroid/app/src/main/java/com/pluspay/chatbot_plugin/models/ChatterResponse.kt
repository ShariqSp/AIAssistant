package com.pluspay.chatbot_plugin.models

import com.google.gson.annotations.SerializedName

data class ChatterResponse(
    @SerializedName("generated_text")
    val generatedText: String
)
