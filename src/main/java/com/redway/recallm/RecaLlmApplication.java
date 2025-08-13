package com.redway.recallm;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RecaLlmApplication {
  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.load();
    String openAiApiKey = dotenv.get("OPENAI_API_KEY");
    System.setProperty("openai.apiKey", openAiApiKey);
    SpringApplication.run(RecaLlmApplication.class, args);
  }
}
