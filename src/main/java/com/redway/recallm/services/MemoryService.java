package com.redway.recallm.services;

import com.openai.models.ChatModel;
import com.redway.recallm.clients.GptClient;
import com.redway.recallm.models.Memory;
import com.redway.recallm.models.Memory.Role;
import com.redway.recallm.repositories.MemoryRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryService {

  private final MemoryRepository repo;
  private final GptClient gptClient;
  private final PromptService promptService;
  
  @Value("${recallm.memory.session-window-limit:10000}")
  private int sessionWindowLimit;

  // Used to generate a summary of an entire session
  public static final ChatModel SUMMARY_MODEL = ChatModel.GPT_4O;

  public void memorise(Memory memory) {
    repo.save(memory);
  }

  public List<Memory> constructShortTermMemory(String userId, String currentSessionId) {
    List<Memory> currentSessionHistory = recallSession(currentSessionId, userId);
    if (!currentSessionHistory.isEmpty()) {
      return new ArrayList<>(currentSessionHistory);
    }
    /*
     * 1. Find the last session id by retrieving the last memory of the user.
     * 2. Collect that session
     * 3. Compress the session using AI summarisation
     * 4. Write the summary as a new memory at the start of this session
     */
    return repo.findTopByUserIdOrderByCreatedAtDesc(userId)
        .map(Memory::getSessionId)
        .map(
            id -> {
              List<Memory> lastSessionHistory = recallSession(id, userId);
              Memory compressedSession =
                  compressSessionHistory(lastSessionHistory, currentSessionId, userId);
              memorise(compressedSession);
              return new ArrayList<>(List.of(compressedSession));
            })
        .orElseGet(ArrayList::new);
  }

  private List<Memory> recallSession(String sessionId, String userId) {
    var page =
        repo.findBySessionIdAndUserId(
            sessionId,
            userId,
            PageRequest.of(0, sessionWindowLimit, Sort.by("createdAt").ascending()));
    return page.getContent();
  }

  private Memory compressSessionHistory(
      List<Memory> sessionHistory, String sessionId, String userId) {
    String prompt = promptService.generateSummaryPromptFromHistory(sessionHistory);
    String summary = gptClient.getResponseText(prompt, MemoryService.SUMMARY_MODEL);
    String content = promptService.formatSessionSummary(summary);
    return new Memory(userId, sessionId, Role.SYSTEM, content);
  }
}
