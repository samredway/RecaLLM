package com.redway.recallm.controllers;

import com.redway.recallm.services.ChatService;
import com.redway.recallm.services.MemoryService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ChatController {

  private final ChatService chatService;
  private final MemoryService memoryService;

  @PostMapping("/chat")
  public String sendMessage(@RequestBody String message) {
    String answer = chatService.chat(message);
    return answer;
  }
}
