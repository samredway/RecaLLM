package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.Memory;
import com.redway.recallm.models.Memory.Role;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration tests require:
 *
 * <p>- Elasticsearch to be running in its container.
 *
 * <p>- OPENAI_API_KEY to be in .env file or env
 *
 * <p>You can run: {@code docker compose up elasticsearch} from the project root.
 */
@SpringBootTest
class MemoryServiceTest {

  @Autowired MemoryService memoryService;

  private final String mockUserId = UUID.randomUUID().toString();
  private final String mockSessionId = UUID.randomUUID().toString();

  private void createSession() {
    var item1 =
        new Memory(
            mockUserId, mockSessionId, Role.USER, "Hello Chat Assistant how nice to meet you!");
    var item2 =
        new Memory(
            mockUserId, mockSessionId, Role.ASSISTANT, "Hello User what can I do for you today?");
    memoryService.memorise(item1);
    memoryService.memorise(item2);
  }

  @Test
  void constructShortTermMemory_retreiveMemory() {
    createSession();
    var history = memoryService.constructShortTermMemory(mockUserId, mockSessionId);
    assertEquals(2, history.size());
  }

  @Test
  void constructShortTermMemory_handlesEmptySession() {
    createSession();
    var history = memoryService.constructShortTermMemory(mockUserId, UUID.randomUUID().toString());
    assertEquals(1, history.size()); // should contain a summary of the mocked session
  }

  @Test
  void constructShortTermMemory_handlesEmptySessionNoPreviousSession() {
    var history = memoryService.constructShortTermMemory(mockUserId, UUID.randomUUID().toString());
    assertEquals(0, history.size());
  }
}
