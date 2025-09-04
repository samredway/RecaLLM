package com.redway.recallm.clients;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.responses.Response;
import com.openai.models.responses.ResponseCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GptClient {
  private final OpenAIClient client;

  public GptClient(@Value("${openai.api.key}") String apiKey) {
    if (apiKey == null || apiKey.isEmpty()) {
      throw new IllegalStateException("Missing required property: openapi.api.key");
    }
    client = OpenAIOkHttpClient.builder().apiKey(apiKey).build();
  }

  public String getResponseText(String message, ChatModel model) {
    ResponseCreateParams params =
        ResponseCreateParams.builder().input(message).model(model).build();
    Response response = client.responses().create(params);
    return textFromResponse(response);
  }

  private String textFromResponse(Response response) {
    return response.output().stream()
        .findFirst()
        .flatMap(item -> item.message()) // returns Optional<ResponseMessage>
        .flatMap(msg -> msg.content().stream().findFirst()) // Optional<Content>
        .flatMap(content -> content.outputText()) // Optional<OutputText>
        .map(outputText -> outputText.text()) // Optional<String>
        .orElse("");
  }
}
