package com.redway.recallm.controllers;

import com.redway.recallm.services.ChatService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/chat")
  public String sendMessage(@RequestBody String message) {
    String answer = chatService.chat(message);
    return answer;
  }
}
