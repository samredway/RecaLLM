package com.redway.recallm.api.controllers;

import com.redway.recallm.api.dtos.ChatRequest;
import com.redway.recallm.api.dtos.ChatResponse;
import com.redway.recallm.services.ChatOrchestratorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class ChatController {

  private final ChatOrchestratorService chatOrchestrator;

  @PostMapping("/chat")
  public ChatResponse sendMessage(@Valid @RequestBody ChatRequest request) {
    return chatOrchestrator.handleTurn(request);
  }
}
