package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.Memory;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"recallm.memory.max-prompt-tokens=50000"})
class PrompServiceTest {
  @Autowired private PromptService promptService;

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
