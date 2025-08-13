package com.redway.recallm.chat.service;

import com.openai.models.ChatModel;
import org.springframework.stereotype.Service;

@Service
public class GPT4oChatService implements ChatService {

  private final ChatModel model = ChatModel.GPT_4O;
  private final GPTClient client;

  public GPT4oChatService(GPTClient client) {
    this.client = client;
  }

  public String chat(String message) {
    return client.getResponseText(message, model);
  }
}
