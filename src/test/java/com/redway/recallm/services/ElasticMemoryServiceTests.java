package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.domain.MemoryItem;
import com.redway.recallm.models.domain.MemoryItem.Role;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests: requires Elasticsearch to be running in its container.
 *
 * <p>You can run: {@code docker compose up elasticsearch} from the project root.
 */
@SpringBootTest
class ElasticMemoryServiceTests {

  @Autowired ElasticMemoryService memoryService;

  @Test
  void testRememberAndRetreive() {
    var userId = UUID.randomUUID().toString();
    var sessionId = UUID.randomUUID().toString();
    var item1 = new MemoryItem(userId, sessionId, Role.USER, "Some content");
    var item2 = new MemoryItem(userId, sessionId, Role.USER, "Some other content");
    memoryService.memorise(item1);
    memoryService.memorise(item2);
    memoryService.forceRefresh();
    List<MemoryItem> history = memoryService.recallSession(sessionId);
    assertEquals(2, history.size());
  }
}
