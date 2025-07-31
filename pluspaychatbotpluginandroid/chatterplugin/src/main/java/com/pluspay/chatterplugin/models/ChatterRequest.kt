package com.pluspay.chatterplugin.models

data class ChatterRequest(
    val prompt: String? = "",
    val tenant : String ?= "pluspay"
)
