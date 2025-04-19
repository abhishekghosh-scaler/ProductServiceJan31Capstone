package com.scaler.productservicejan31capstone.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestChatController
{
    ChatClient chatClient;

    public TestChatController(ChatClient chatClient)
    {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message)
    {
        return chatClient.prompt().user(message).call().content();
    }

}
