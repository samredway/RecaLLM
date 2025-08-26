package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.redway.recallm.api.dtos.ChatRequest;
import com.redway.recallm.models.Memory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ChatOrchestratorServiceTest {

  @Mock MemoryService memoryService;
  @Mock ChatService chatService;
  @Mock PromptService promptService;

  @InjectMocks ChatOrchestratorService orchestrator;

  @Test
  void handleTurn_handlesRequest() {
    var req = new ChatRequest("hi", "user1", "sess1");
    var history = List.of(new Memory("user1", "sess1", Memory.Role.USER, "hi"));
    when(memoryService.recallSession("sess1")).thenReturn(history);
    when(promptService.generatePromptFromShortTermMemory(history)).thenReturn("PROMPT");
    when(chatService.chat("PROMPT")).thenReturn("ANSWER");

    var answer = orchestrator.handleTurn(req);

    assertEquals("ANSWER", answer);

    verify(memoryService, times(2)).memorise(any(Memory.class));
    verify(memoryService).recallSession("sess1");
    verify(promptService).generatePromptFromShortTermMemory(history);
    verify(chatService).chat("PROMPT");
    verifyNoMoreInteractions(memoryService, promptService, chatService);
  }
}
