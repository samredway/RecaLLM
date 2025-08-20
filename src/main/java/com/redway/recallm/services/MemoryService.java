package com.redway.recallm.services;

import com.redway.recallm.models.domain.MemoryItem;
import java.util.List;

public interface MemoryService {
  // Write memory to storage
  void remember(MemoryItem item);

  // Retreive all memories related to this session
  List<MemoryItem> sessionHistory(String sessionId);
}
