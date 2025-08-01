package com.bharat_yatra.chat_bot_plugin.screens

import android.text.TextUtils
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharat_yatra.chat_bot_plugin.models.AppGuideStepsRequest
import com.bharat_yatra.chat_bot_plugin.models.ChatMessage
import com.bharat_yatra.chat_bot_plugin.network.ChatterApiService
import com.bharat_yatra.chat_bot_plugin.models.ChatterRequest
import com.bharat_yatra.chat_bot_plugin.models.GeneralPromptRequest
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    open val messages = _messages.asStateFlow()
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isLinearLoading = MutableStateFlow(false)
    val isLinearLoading: StateFlow<Boolean> = _isLinearLoading.asStateFlow()

    private val generalPromptApiService = ChatterApiService.getGeneralPromptsService()
    private val plusPayGuideStepsApiService = ChatterApiService.getPlusPayGuideStepsService()
    private val transactionAndInsightsApiService = ChatterApiService.getChatterApiService()

    private val _chatMode = MutableStateFlow(ChatMode.NORMAL)
    val chatMode: StateFlow<ChatMode> = _chatMode.asStateFlow()

    init {
        // Optionally, you can initialize with a welcome message
        _messages.value = listOf(ChatMessage("Welcome to the chat! How can I assist you today?", isUser = false, isBotFirstMessage = true))
    }

    fun setChatMode(mode: ChatMode) {
        _chatMode.value = mode
    }

    open fun generalPrompt(message: String?) {
        if (!message.isNullOrBlank()) {
            val updated = _messages.value.toMutableList()
            updated.add(ChatMessage(message, isUser = true))
            _messages.value = updated
        }

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = generalPromptApiService.plusPayGeneralPrompts(
                    GeneralPromptRequest(
                        question = message,
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

    open fun plusPayAppGuide(message: String?) {
        if(!message.isNullOrBlank()){
            val updated = _messages.value.toMutableList()
            updated.add(ChatMessage(message, isUser = true))
            _messages.value = updated
            _isLinearLoading.value = false
            _isLoading.value = true
        } else {
            _isLinearLoading.value = true
            _isLoading.value = false
        }

        viewModelScope.launch {
            try {
                val response = plusPayGuideStepsApiService.plusPayGuideSteps(
                    AppGuideStepsRequest(
                        question = if(TextUtils.isEmpty(message)) "Guide to use Bharath Yathra Card" else message,
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
                _isLinearLoading.value = false
                _isLoading.value = false
            }
        }
    }


    open fun transactionAndInsights(message: String?) {
        if(!message.isNullOrBlank()){
            val updated = _messages.value.toMutableList()
            updated.add(ChatMessage(message, isUser = true))
            _messages.value = updated
            _isLinearLoading.value = false
            _isLoading.value = true
        } else {
            _isLinearLoading.value = true
            _isLoading.value = false
        }

        viewModelScope.launch {
            try {
                val response = transactionAndInsightsApiService.sendMessage(
                    ChatterRequest(
                        prompt = if(TextUtils.isEmpty(message)) "Summarize my last month transaction" else message,
                    )
                )
                val botReply = response.generatedText
                val newList = _messages.value.toMutableList()
                newList.add(ChatMessage(botReply, isUser = false))
                _messages.value = newList
            } catch (e: Exception) {
                val newList = _messages.value.toMutableList()
                newList.add(ChatMessage("Failed to get response. Please try again.", isUser = false))
                _messages.value = newList
            } finally {
                _isLinearLoading.value = false
                _isLoading.value = false
            }
        }
    }


    fun resetChat() {
        setChatMode(ChatMode.NORMAL)
        _messages.value = listOf(
            ChatMessage(
                "Welcome to the chat! How can I assist you today?",
                isUser = false,
                isBotFirstMessage = true
            )
        )
    }

}

