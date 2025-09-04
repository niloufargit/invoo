package com.invoo.orchestrator.domaine.entity.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPassword(
        @NotNull(message = "Please provide a valid email. It should not be null")
        @NotBlank(message = "Please provide a valid email. It should not be empty")
        String email
) {}
