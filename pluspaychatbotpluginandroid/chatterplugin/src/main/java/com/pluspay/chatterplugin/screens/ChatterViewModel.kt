package com.pluspay.chatterplugin.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pluspay.chatterplugin.network.ChatterApiService
import com.pluspay.chatterplugin.models.ChatMessage
import com.pluspay.chatterplugin.models.GeneralPromptRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    open val messages = _messages.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val generalPromptApiService = ChatterApiService.getGeneralPromptsService()
    private val plusPayGuideStepsApiService = ChatterApiService.getPlusPayGuideStepsService()

    init {
        // Optionally, you can initialize with a welcome message
        _messages.value = listOf(ChatMessage("Welcome to the chat! How can I assist you today?", isUser = false, isBotFirstMessage = true))
    }

    open fun sendMessage(message: String) {
        val updated = _messages.value.toMutableList()
        updated.add(ChatMessage(message, isUser = true))
        _messages.value = updated

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = generalPromptApiService.plusPayGeneralPrompts(
                    GeneralPromptRequest(
                        question = message, tenant = "pluspay"
                    )
                )
                val botReply = response.response
                val newList = _messages.value.toMutableList()
                newList.add(ChatMessage(botReply, isUser = false))
                _messages.value = newList
            } catch (e: Exception) {
                val newList = _messages.value.toMutableList()
                newList.add(ChatMessage("Failed to get response. Please try again.", isUser = false))
                _messages.value = newList
            } finally {
                _isLoading.value = false
            }
        }
    }


}

