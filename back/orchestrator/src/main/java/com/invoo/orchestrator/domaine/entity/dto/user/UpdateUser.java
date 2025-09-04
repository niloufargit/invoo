package com.invoo.orchestrator.domaine.entity.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class UpdateUser {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
}
