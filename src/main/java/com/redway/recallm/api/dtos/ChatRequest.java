package com.redway.recallm.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ChatRequest(
    @NotBlank @Size(max = 10_000) String message,
    @NotBlank @Size(max = 200) String userId,
    @Size(max = 200) String sessionId) {}
