package com.redway.recallm.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.redway.recallm.models.domain.MemoryItem;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ElasticMemoryService implements MemoryService {

  private final ElasticsearchClient client;

  /** Save a memory to elastic */
  @Override
  public void remember(MemoryItem item) {}

  /** Retrieve all memories from a given session */
  @Override
  public List<MemoryItem> sessionHistory(String sessionId) {
    return new ArrayList<MemoryItem>();
  }
}
