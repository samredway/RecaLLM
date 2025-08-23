package com.redway.recallm.services;

import com.redway.recallm.mappers.MemoryItemMapper;
import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.repositories.MemoryItemDocumentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class ElasticMemoryService implements MemoryService {

  private final MemoryItemDocumentRepository repo;
  private final MemoryItemMapper mapper;

  // Will be used for long term memory searches which will require more complex queries
  // private final ElasticsearchClient client;

  @Override
  public void memorise(MemoryItem item) {
    repo.save(mapper.toDocument(item));
  }

  @Override
  public List<MemoryItem> recallSession(String sessionId) {
    return repo.findBySessionIdOrderByCreatedAtAsc(sessionId).stream()
        .map(mapper::toDomain)
        .toList();
  }
}
