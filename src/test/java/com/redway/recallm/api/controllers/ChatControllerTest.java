package com.redway.recallm.api.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.redway.recallm.api.dtos.ChatRequest;
import com.redway.recallm.api.dtos.ChatResponse;
import com.redway.recallm.services.ChatOrchestratorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private ChatOrchestratorService orchestrator;

  @Test
  void sendMessage_retunsString() throws Exception {
    // Given
    var req = new ChatRequest("hello", "user1", "session1");
    when(orchestrator.handleTurn(any(ChatRequest.class))).thenReturn(new ChatResponse("Response", "session1"));

    // When/Then
    mockMvc
        .perform(
            post("/chat")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(req)))
        .andExpect(status().isOk());
  }
}
