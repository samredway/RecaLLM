package com.redway.recallm.services;

import com.redway.recallm.models.Memory;
import com.redway.recallm.repositories.MemoryItemDocumentRepository;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemoryService {

  private final MemoryItemDocumentRepository repo;

  public enum Role {
    USER,
    ASSISTENT,
  }

  public void memorise(Memory memory) {
    repo.save(memory);
  }

  public List<Memory> recallSession(String sessionId) {
    var es_window_limit = 10_000;
    var page =
        repo.findBySessionId(
            sessionId, PageRequest.of(0, es_window_limit, Sort.by("createdAt").ascending()));

    return page.getContent();
  }

  public Memory createMemory(String userId, String sessionId, Role role, String content) {
    var createdAt = OffsetDateTime.now();
    return new Memory(null, userId, sessionId, role.name(), content, createdAt);
  }
}
