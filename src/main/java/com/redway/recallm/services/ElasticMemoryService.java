package com.redway.recallm.services;

import com.redway.recallm.models.domain.MemoryItem;
import java.util.ArrayList;
import java.util.List;

class ElasticMemoryService implements MemoryService {

  @Override
  public void remember(MemoryItem item) {}

  @Override
  public List<MemoryItem> sessionHistory(String sessionId) {
    return new ArrayList<MemoryItem>();
  }
}
