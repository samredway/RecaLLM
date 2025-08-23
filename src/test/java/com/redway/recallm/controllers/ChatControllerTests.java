package com.redway.recallm.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.redway.recallm.services.ChatService;
import com.redway.recallm.services.MemoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ChatService chatService;
  @MockBean private MemoryService memoryService;

  @Test
  void sendMessage_retunsString() throws Exception {
    // Given
    when(chatService.chat(anyString())).thenReturn("Response"); // setup mock

    // When/Then
    mockMvc
        .perform(post("/chat").contentType(MediaType.TEXT_PLAIN).content("Hello"))
        .andExpect(status().isOk());
  }
}
