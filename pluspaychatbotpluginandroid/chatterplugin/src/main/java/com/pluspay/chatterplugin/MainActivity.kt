package com.pluspay.chatterplugin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.pluspay.chatterplugin.screens.ChatViewModel
import com.pluspay.chatterplugin.screens.ChatterScreen
import com.pluspay.chatterplugin.ui.theme.PluspaychatbotpluginandroidTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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