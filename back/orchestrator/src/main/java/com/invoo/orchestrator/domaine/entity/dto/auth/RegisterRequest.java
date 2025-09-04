package com.invoo.orchestrator.domaine.entity.dto.auth;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {

    @NotNull(message = "Please provide a valid username. It should not be null")
    @NotBlank(message = "Please provide a valid username. It should not be blank")
    private String firstname;

    @NotNull(message = "Please provide a valid lastname. It should not be null")
    @NotBlank(message = "Please provide a valid lastname. It should not be blank")
    private String lastname;

    @NotNull(message = "Please provide a valid email. It should not be null")
    @NotBlank(message = "Please provide a valid email. It should not be blank")
    private String email;

    @NotNull(message = "Please provide a valid password. It should not be null")
    @NotBlank(message = "Please provide a valid password. It should not be blank")
    private String password;

    @NotNull(message = "Please provide a valid confirm password. It should not be null")
    @NotBlank(message = "Please provide a valid confirm password. It should not be blank")
    private String confirmPassword;

    @NotNull(message = "Please accept the terms and conditions. It should not be null")
    @AssertTrue(message = "Please accept the terms and conditions")
    private boolean terms;
}
