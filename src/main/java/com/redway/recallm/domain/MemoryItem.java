package com.redway.recallm.domain;

import java.time.OffsetTime;
import java.time.ZoneOffset;

public record MemoryItem(
    String userId, String sessionId, Role role, String content, OffsetTime createdAt) {

  public enum Role {
    USER,
    ASSISTANT
  }

  public MemoryItem(String userId, String sessionId, Role role, String content) {
    this(userId, sessionId, role, content, OffsetTime.now(ZoneOffset.UTC));
  }
}
