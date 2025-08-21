package com.redway.recallm.services;

import com.openai.models.ChatModel;
import com.redway.recallm.clients.GptClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class Gpt4oChatService implements ChatService {

  private final ChatModel model = ChatModel.GPT_4O;
  private final GptClient client;

  @Override
  public String chat(String message) {
    return client.getResponseText(message, model);
  }
}
