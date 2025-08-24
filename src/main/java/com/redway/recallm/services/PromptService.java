package com.redway.recallm.services;

import com.redway.recallm.models.Memory;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
  public String generatePromptFromShortTermMemory(List<Memory> shortTermMemory) {
    return shortTermMemory.stream()
        .map(m -> m.getRole() + ": " + m.getContent())
        .collect(Collectors.joining("\n\n"));
  }
}
