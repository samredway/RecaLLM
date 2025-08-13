package com.redway.recallm.chat.web;

import com.redway.recallm.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  @PostMapping("/chat")
  public String sendMessage(@RequestBody String message) {
    String answer = chatService.chat(message);
    return answer;
  }
}
