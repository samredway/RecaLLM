package com.redway.recallm.services;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.redway.recallm.models.domain.MemoryItem;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ElasticMemoryService implements MemoryService {

  private final ElasticsearchClient client;

  @Override
  public void remember(MemoryItem item) {}

  @Override
  public List<MemoryItem> sessionHistory(String sessionId) {
    return new ArrayList<MemoryItem>();
  }
}
