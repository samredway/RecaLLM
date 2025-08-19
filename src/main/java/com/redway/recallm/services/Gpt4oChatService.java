package com.redway.recallm.services;

import com.openai.models.ChatModel;
import com.redway.recallm.clients.GptClient;
import org.springframework.stereotype.Service;

@Service
public class Gpt4oChatService implements ChatService {

  private final ChatModel model = ChatModel.GPT_4O;
  private final GptClient client;

  public Gpt4oChatService(GptClient client) {
    this.client = client;
  }

  @Override
  public String chat(String message) {
    return client.getResponseText(message, model);
  }
}
