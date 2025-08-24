package com.redway.recallm.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.redway.recallm.models.Memory;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PrompServiceTest {
  @Autowired private PromptService promptService;

  @Test
  public void testGeneratePromptFromShortTermMemory() {
    var shortTermMemory = new ArrayList<Memory>();
    var user = "user1";
    var session = "session1";
    shortTermMemory.add(new Memory(user, session, Memory.Role.USER, "Hi"));
    shortTermMemory.add(new Memory(user, session, Memory.Role.ASSISTANT, "Hi back"));
    shortTermMemory.add(new Memory(user, session, Memory.Role.USER, "Whats up?"));
    String prompt = promptService.generatePromptFromShortTermMemory(shortTermMemory);
    String expected = "USER: Hi\n\nASSISTANT: Hi back\n\nUSER: Whats up?";
    assertEquals(expected, prompt);
  }
}
