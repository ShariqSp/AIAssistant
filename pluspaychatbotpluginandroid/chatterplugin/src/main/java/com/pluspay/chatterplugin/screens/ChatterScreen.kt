package com.pluspay.chatterplugin.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.pluspay.chatterplugin.models.ChatMessage
import com.pluspay.chatterplugin.ui.theme.provideChatterColors
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ChatterScreen(
    viewModel: ChatViewModel = ChatViewModel(),
    modifier: Modifier = Modifier.fillMaxSize()
) {
    val messages by viewModel.messages.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val colors = provideChatterColors()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            kotlinx.coroutines.yield()
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    Box(
        modifier = Modifier.background(colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp, start = 16.dp, end = 16.dp, bottom = 60.dp)
        ) {
            LazyColumn(
                state = listState,
                modifier = Modifier.weight(1f),
                reverseLayout = false
            ) {
                items(messages) { message ->
                    ChatBubble(message)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            ) {
                TextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = colors.inputField,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(2.dp),
                    placeholder = { Text("Type your message here...") },
                    enabled = !isLoading,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    ),
                    singleLine = true
                )
                Spacer(modifier = Modifier.width(8.dp))
                AnimatedContent(
                    targetState = isLoading,
                    transitionSpec = { fadeIn() togetherWith fadeOut() }
                ) { loading ->
                    if (loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(30.dp),
                            strokeWidth = 5.dp
                        )
                    } else {
                        Button(
                            onClick = {
                                if (input.isNotBlank()) {
                                    viewModel.sendMessage(input)
                                    input = ""
                                }
                            },
                            enabled = input.isNotBlank()
                        ) {
                            Text("Send")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val colors = provideChatterColors()
    val bgColor = if (message.isUser) colors.userBubble else colors.botBubble
    val textColor = if (message.isUser) colors.userText else colors.botText
    val clickableTextBoxColor = colors.clickableBox
    val alignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Box(
            modifier = Modifier
                .widthIn(max = LocalConfiguration.current.screenWidthDp.dp * 0.8f)
                .background(bgColor, shape = RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            if( message.isBotFirstMessage == true ) {
                Column {
                    Text(
                        text = "I'm your personal PlusPay AI assistant here to help you manage your spending, split bills, track expenses, and get smart insights about your money. Think of me as your financial buddy, always ready to assist!",
                        color = textColor,
                        softWrap = true,
                        textAlign = if (message.isUser) TextAlign.End else TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "\uD83D\uDCA1 I can assist you with:\n" +
                                "Viewing your transaction data" +
                                "\nGenerating smart insights about your spending" +
                                "\nGuiding you through PlusPay app features & steps\n\n" +
                                "\uD83D\uDC47 Please select an option below or just type your query:",
                        color = textColor,
                        softWrap = true,
                        textAlign = if (message.isUser) TextAlign.End else TextAlign.Start,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                clickableTextBoxColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Text(
                            text = "\uD83D\uDD0D Transactions & Insights",
                            color = textColor,
                            softWrap = true,
                            textAlign = TextAlign.Center
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Box(
                        modifier = Modifier
                            .background(
                                clickableTextBoxColor,
                                shape = RoundedCornerShape(5.dp)
                            )
                            .padding(8.dp)
                            .fillMaxWidth()
                            .clickable {

                            },
                    ) {
                        Text(
                            text = "\uD83D\uDCF1 PlusPay App Guide",
                            color = textColor,
                            softWrap = true,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                Text(
                    text = message.text,
                    color = textColor,
                    softWrap = true,
                    textAlign = if (message.isUser) TextAlign.End else TextAlign.Start,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ChatterScreenPreview() {
    val sampleMessages = listOf(
        ChatMessage("Hello!", isUser = false),
        ChatMessage("Hi there!", isUser = true),
        ChatMessage("How can I help you?", isUser = false),
        ChatMessage("Sure! \uD83D\uDCB0 Your current wallet balance is ₹1,250. Would you like to add more funds or view your recent transactions?", isUser = false)
    )
    val fakeViewModel = object : ChatViewModel() {
        override val messages: StateFlow<List<ChatMessage>> = MutableStateFlow(sampleMessages)
        override fun sendMessage(text: String) {}
    }
    ChatterScreen(viewModel = fakeViewModel)
}