package com.redway.recallm.models.domain;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public record MemoryItem(
    String userId, String sessionId, Role role, String content, OffsetDateTime createdAt) {

  public enum Role {
    USER,
    ASSISTANT
  }

  public MemoryItem(String userId, String sessionId, Role role, String content) {
    this(userId, sessionId, role, content, OffsetDateTime.now(ZoneOffset.UTC));
  }
}
