package com.redway.recallm.mappers;

import static org.junit.jupiter.api.Assertions.*;

import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.domain.MemoryItem.Role;
import com.redway.recallm.models.elastic.MemoryItemDocument;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

public class MemoryItemMapperTests {

  private final MemoryItemMapper mapper = Mappers.getMapper(MemoryItemMapper.class);

  @Test
  public void testToDocument() {
    // Given
    MemoryItem item = new MemoryItem("user1", "session1", Role.USER, "Hello");

    // When
    MemoryItemDocument doc = mapper.toDocument(item);

    // Then
    assertEquals("user1", doc.getUserId());
    assertEquals("session1", doc.getSessionId());
    assertEquals("Hello", doc.getContent());
    assertEquals("USER", doc.getRole()); // enum converted to string
    assertNull(doc.getId()); // default should be null
    assertEquals(doc.getCreatedAt(), item.createdAt());
  }

  @Test
  public void testToDomain() {
    // Given
    OffsetDateTime now = OffsetDateTime.now();
    MemoryItemDocument doc =
        new MemoryItemDocument("user1", "session1", "ASSISTANT", "Hi there", now);

    // When
    MemoryItem item = mapper.toDomain(doc);

    // Then
    assertEquals("user1", item.userId());
    assertEquals("session1", item.sessionId());
    assertEquals(Role.ASSISTANT, item.role());
    assertEquals("Hi there", item.content());
    assertEquals(now, item.createdAt());
  }
}
