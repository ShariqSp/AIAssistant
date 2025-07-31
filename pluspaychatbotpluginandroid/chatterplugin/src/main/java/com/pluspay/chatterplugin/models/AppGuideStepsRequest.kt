package com.pluspay.chatterplugin.models

data class AppGuideStepsRequest(
    val question: String? = "",
    val tenant: String = "pluspay"
)
