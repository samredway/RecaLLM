package com.redway.recallm.services;

import com.redway.recallm.constants.PromptConstants;
import com.redway.recallm.exceptions.SessionTooLargeException;
import com.redway.recallm.models.Memory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

  @Value("${recallm.memory.max-prompt-tokens}")
  private int maxPromptTokens;

  public String generatePromptFromShortTermMemory(List<Memory> shortTermMemory) {
    String prompt = joinHistory(shortTermMemory);
    
    int estimatedTokens = estimateTokens(prompt);
    if (estimatedTokens > maxPromptTokens) {
      throw new SessionTooLargeException(
          String.format("Prompt too large: %d tokens (max: %d). Session needs rotation.", 
              estimatedTokens, maxPromptTokens));
    }
    
    return prompt;
  }

  public String generateSummaryPromptFromHistory(List<Memory> history) {
    String joined = joinHistory(history);
    return PromptConstants.SUMMARY_PROMPT + joined;
  }

  public String formatSessionSummary(String summary) {
    return PromptConstants.SESSION_SUMMARY_PREFIX + summary;
  }

  /**
   * Estimates the number of tokens in a given text using a conservative word-based approach.
   * 
   * <p>This is a deliberately simple and conservative estimation strategy that assumes 
   * 2 tokens per word (determined by whitespace splitting). While this is less precise 
   * than using the actual GPT-4o tokenizer, it serves our needs because:
   * 
   * <ul>
   *   <li><strong>Safety margin:</strong> Conservative estimates prevent API limit violations</li>
   *   <li><strong>Operational flexibility:</strong> Safe to increase token limits without fear</li>
   *   <li><strong>Model independence:</strong> Works regardless of tokenizer differences</li>
   *   <li><strong>Performance:</strong> Fast computation suitable for request-time processing</li>
   * </ul>
   * 
   * <p>Given our current 30K token limit against GPT-4o's 128K context window, even 
   * significant estimation errors maintain substantial safety margins.
   * 
   * <p><strong>Future optimization:</strong> This could be replaced with more accurate 
   * token counting (e.g., tiktoken library or model-specific tokenizers) if precision 
   * becomes critical for cost optimization or tighter limit management.
   * 
   * @param text the text to estimate token count for
   * @return estimated number of tokens (conservative estimate)
   */
  private int estimateTokens(String text) {
    if (text == null || text.trim().isEmpty()) {
      return 0;
    }
    
    // Split on any whitespace and multiply by 2 (conservative estimate)
    return text.trim().split("\\s+").length * 2;
  }

  private String joinHistory(List<Memory> history) {
    return history.stream()
        .map(m -> m.getRole() + ": " + m.getContent())
        .collect(Collectors.joining("\n\n"));
  }
}
