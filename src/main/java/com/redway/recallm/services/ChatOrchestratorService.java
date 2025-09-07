package com.redway.recallm.services;

import com.redway.recallm.api.dtos.ChatRequest;
import com.redway.recallm.api.dtos.ChatResponse;
import com.redway.recallm.exceptions.SessionTooLargeException;
import com.redway.recallm.models.Memory;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
@Slf4j
public class ChatOrchestratorService {

  private final MemoryService memoryService;
  private final PromptService promptService;
  private final ChatService chatService;

  @Transactional
  public ChatResponse handleTurn(ChatRequest request) {
    // Generate sessionId if not provided
    String sessionId =
        request.sessionId() != null ? request.sessionId() : UUID.randomUUID().toString();
    boolean isNewSession = request.sessionId() == null;

    log.info(
        "Processing chat for user: {} session: {} (new: {})",
        request.userId(),
        sessionId,
        isNewSession);

    try {
      return processChatTurn(request.userId(), sessionId, request.message());
    } catch (SessionTooLargeException e) {
      log.info(
          "Session {} too large for user {}, rotating to new session", sessionId, request.userId());

      String newSessionId = UUID.randomUUID().toString();
      ChatResponse response = processChatTurn(request.userId(), newSessionId, request.message());

      log.info("Rotated to session {} for user {}", newSessionId, request.userId());
      return response;
    }
  }

  private ChatResponse processChatTurn(String userId, String sessionId, String message) {
    var newUserMemory = new Memory(userId, sessionId, Memory.Role.USER, message);
    // Retrieve memory before adding chat so any system memories can be added in order
    List<Memory> shortTermMemory = memoryService.constructShortTermMemory(userId, sessionId);
    // Now add memory
    memoryService.memorise(newUserMemory);
    shortTermMemory.add(newUserMemory);

    String prompt = promptService.generatePromptFromShortTermMemory(shortTermMemory);
    String answer = chatService.chat(prompt);

    var newAssistantMemory = new Memory(userId, sessionId, Memory.Role.ASSISTANT, answer);
    memoryService.memorise(newAssistantMemory);

    return new ChatResponse(answer, sessionId);
  }
}
