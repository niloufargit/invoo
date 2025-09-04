package com.invoo.orchestrator.domaine.entity.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ResetPasswordToken(
        @NotNull(message = "Please provide a valid password. It should not be null")
        @NotBlank(message = "Please provide a valid password. It should not be empty")
        String password,
        @NotNull(message = "Please provide a valid confirm password. It should not be null")
        @NotBlank(message = "Please provide a valid confirm password. It should not be empty")
        String confirmPassword
) {}
