package com.invoo.orchestrator.domaine.entity.dto.auth;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record VerifiedUserRequest(
        @NotNull(message = "Please provide a valid email.")
        @NotBlank(message = "Please provide a valid email. It should not be empty")
        String email
) {}
