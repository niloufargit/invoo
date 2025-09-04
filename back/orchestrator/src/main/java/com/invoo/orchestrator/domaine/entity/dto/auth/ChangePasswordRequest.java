package com.invoo.orchestrator.domaine.entity.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ChangePasswordRequest(
        @NotNull(message = "Current password is required")
        @NotBlank(message = "Current password is required")
        String currentPassword,
        @NotNull(message = "New password is required")
        @NotBlank(message = "New password is required")
        String newPassword,
        @NotNull(message = "Confirm new password is required")
        @NotBlank(message = "Confirm new password is required")
        String confirmNewPassword
) {
}
