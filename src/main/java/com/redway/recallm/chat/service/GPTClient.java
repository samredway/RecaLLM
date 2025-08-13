package com.redway.recallm.chat.service;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.stereotype.Service;

@Service
public class GPTClient {
  public final OpenAIClient client = OpenAIOkHttpClient.fromEnv();

  private String textFromResponse(Response response) {
    return response.output().stream()
      .findFirst()
      .flatMap(item -> item.message()) // returns Optional<ResponseMessage>
      .flatMap(msg -> msg.content().stream().findFirst()) // stream → findFirst → Optional<Content>
      .flatMap(content -> content.outputText()) // Optional<OutputText>
      .map(outputText -> outputText.text()) // Optional<String>
      .orElse("");
  }

  public String getResponseText(String message, ChatModel model) {
    ResponseCreateParams params =
        ResponseCreateParams.builder().input(message).model(model).build();
    Response response = client.responses().create(params);
    return textFromResponse(response);
  }
}
