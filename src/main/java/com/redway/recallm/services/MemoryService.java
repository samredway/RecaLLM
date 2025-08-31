package com.redway.recallm.services;

import com.openai.models.ChatModel;
import com.redway.recallm.clients.GptClient;
import com.redway.recallm.models.Memory;
import com.redway.recallm.models.Memory.Role;
import com.redway.recallm.repositories.MemoryItemDocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryService {

  private final MemoryItemDocumentRepository repo;
  private final GptClient gptClient;
  private final PromptService promptService;

  // Used to generate a summary of an entire session
  public static final ChatModel SUMMARY_MODEL = ChatModel.GPT_4O;

  public void memorise(Memory memory) {
    repo.save(memory);
  }

  public List<Memory> constructShortTermMemory(String userId, String currentSessionId) {
    List<Memory> currentSessionHistory = recallSession(currentSessionId);
    if (!currentSessionHistory.isEmpty()) {
      return currentSessionHistory;
    }
    /*
     * 1. Find the last session id by retrieving the last memory of the user.
     * 2. Collect that session
     * 3. Compress the session using AI summarisation
     * 4. Right the summary as a new memory at the start of this session
     */
    return repo.findTopByUserIdOrderByCreatedAtDesc(userId)
        .map(Memory::getSessionId)
        .map(
            id -> {
              List<Memory> lastSessionHistory = recallSession(id);
              Memory compressedSession =
                  compressSessionHistory(lastSessionHistory, currentSessionId, userId);
              memorise(compressedSession);
              return compressedSession;
            })
        .map(List::of)
        .orElseGet(List::of);
  }

  private List<Memory> recallSession(String sessionId) {
    var es_window_limit = 10_000;
    var page =
        repo.findBySessionId(
            sessionId, PageRequest.of(0, es_window_limit, Sort.by("createdAt").ascending()));
    return page.getContent();
  }

  private Memory compressSessionHistory(
      List<Memory> sessionHistory, String sessionId, String userId) {
    String prompt = promptService.generateSummaryPromptyFromHistory(sessionHistory);
    String summary = gptClient.getResponseText(prompt, MemoryService.SUMMARY_MODEL);
    return new Memory(userId, sessionId, Role.SYSTEM, summary);
  }
}
