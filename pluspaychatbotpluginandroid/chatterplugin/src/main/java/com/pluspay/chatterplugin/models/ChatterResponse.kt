package com.pluspay.chatterplugin.models

import com.google.gson.annotations.SerializedName

data class ChatterResponse(
    @SerializedName("generated_text")
    val generatedText: String
)
