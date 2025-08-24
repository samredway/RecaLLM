package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.Memory;
import com.redway.recallm.models.Memory.Role;
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
class MemoryServiceTest {

  @Autowired MemoryService memoryService;

  @Test
  void testRememberAndRetreive() {
    var userId = UUID.randomUUID().toString();
    var sessionId = UUID.randomUUID().toString();
    var item1 = new Memory(userId, sessionId, Role.USER, "Some content");
    var item2 = new Memory(userId, sessionId, Role.USER, "Some other content");
    memoryService.memorise(item1);
    memoryService.memorise(item2);
    var history = memoryService.recallSession(sessionId);
    assertEquals(2, history.size());
  }
}
