package com.redway.recallm.services;

import com.redway.recallm.api.dtos.ChatRequest;
import com.redway.recallm.models.Memory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class ChatOrchestratorService {

  private final MemoryService memoryService;
  private final PromptService promptService;
  private final ChatService chatService;

  @Transactional
  public String handleTurn(ChatRequest request) {
    var newUserMemory =
        new Memory(request.userId(), request.sessionId(), Memory.Role.USER, request.message());
    memoryService.memorise(newUserMemory);
    var shortTermMemory = memoryService.recallSession(request.sessionId());
    String prompt = promptService.generatePromptFromShortTermMemory(shortTermMemory);
    String answer = chatService.chat(prompt);
    var newAssistantMemory =
        new Memory(request.userId(), request.sessionId(), Memory.Role.ASSISTANT, answer);
    memoryService.memorise(newAssistantMemory);
    return answer;
  }
}
