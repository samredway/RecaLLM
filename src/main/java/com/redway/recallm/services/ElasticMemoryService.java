package com.redway.recallm.services;

import com.redway.recallm.mappers.MemoryItemMapper;
import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.repositories.MemoryItemDocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ElasticMemoryService implements MemoryService {

  private final MemoryItemDocumentRepository repo;
  private final MemoryItemMapper mapper;

  @Override
  public void memorise(MemoryItem item) {
    repo.save(mapper.toDocument(item));
  }

  @Override
  public List<MemoryItem> recallSession(String sessionId) {
    var es_window_limit = 10_000;
    var page =
        repo.findBySessionId(
            sessionId, PageRequest.of(0, es_window_limit, Sort.by("createdAt").ascending()));

    return page.getContent().stream().map(mapper::toDomain).toList();
  }
}
