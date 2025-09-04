package com.invoo.orchestrator.domaine.entity.dto.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotNull(message = "Please provide a valid email. It should not be null")
    @NotBlank(message = "Please provide a valid email. It should not be empty")
    private String email;
    @NotNull(message = "Please provide a valid password. It should not be null")
    @NotBlank(message = "Please provide a valid password. It should not be empty")
    private String password;
}
