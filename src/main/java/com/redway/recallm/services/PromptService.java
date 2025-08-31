package com.redway.recallm.services;

import com.redway.recallm.models.Memory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
  /*
   * Used to summarise last session so as to pull it into short term memory
   * currently allows 300 words to summarise. This may need to be tweaked and
   * played with
   */
  public static final String SUMMARY_PROMPT =
      "Please take the following text and summarise it in such a way that you can continue a"
          + " conversation without losing context but also reducing it to its most concises form."
          + " This summary should be no more than 500 words. You should only return the summary"
          + " itself and no other text. This summary is used by a programatic process\n\n";

  public String generatePromptFromShortTermMemory(List<Memory> shortTermMemory) {
    return joinHistory(shortTermMemory);
  }

  public String generateSummaryPromptyFromHistory(List<Memory> history) {
    String joined = joinHistory(history);
    return SUMMARY_PROMPT + joined;
  }

  private String joinHistory(List<Memory> history) {
    return history.stream()
        .map(m -> m.getRole() + ": " + m.getContent())
        .collect(Collectors.joining("\n\n"));
  }
}
