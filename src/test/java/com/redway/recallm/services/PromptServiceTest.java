package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.Memory;
import java.util.List;
import org.junit.jupiter.api.Test;

class PrompServiceTest {
  private final PromptService promptService = new PromptService();

  @Test
  public void testGeneratePromptFromShortTermMemory() {
    var user = "user1";
    var session = "session1";
    var shortTermMemory =
        List.of(
            new Memory(user, session, Memory.Role.USER, "Hi"),
            new Memory(user, session, Memory.Role.ASSISTANT, "Hi back"),
            new Memory(user, session, Memory.Role.USER, "Whats up?"));
    String prompt = promptService.generatePromptFromShortTermMemory(shortTermMemory);
    String expected = "USER: Hi\n\nASSISTANT: Hi back\n\nUSER: Whats up?";
    assertEquals(expected, prompt);
  }
}
