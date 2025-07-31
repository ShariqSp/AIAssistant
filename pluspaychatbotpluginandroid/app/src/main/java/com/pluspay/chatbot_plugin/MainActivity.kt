package com.pluspay.chatbot_plugin

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pluspay.chatbot_plugin.screens.ChatViewModel
import com.pluspay.chatbot_plugin.screens.ChatterScreen
import com.pluspay.chatbot_plugin.ui.theme.PluspaychatbotpluginandroidTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PluspaychatbotpluginandroidTheme {
                val vm: ChatViewModel by viewModels()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        ChatterScreen(
                            viewModel = vm,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(it) // Apply padding to avoid content being obscured by system bars
                        )
                    }
                )
            }
        }
    }
}