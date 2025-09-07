package com.redway.recallm.services;

import com.redway.recallm.constants.PromptConstants;
import com.redway.recallm.models.Memory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

  public String generatePromptFromShortTermMemory(List<Memory> shortTermMemory) {
    return joinHistory(shortTermMemory);
  }

  public String generateSummaryPromptFromHistory(List<Memory> history) {
    String joined = joinHistory(history);
    return PromptConstants.SUMMARY_PROMPT + joined;
  }

  public String formatSessionSummary(String summary) {
    return PromptConstants.SESSION_SUMMARY_PREFIX + summary;
  }

  private String joinHistory(List<Memory> history) {
    return history.stream()
        .map(m -> m.getRole() + ": " + m.getContent())
        .collect(Collectors.joining("\n\n"));
  }
}
