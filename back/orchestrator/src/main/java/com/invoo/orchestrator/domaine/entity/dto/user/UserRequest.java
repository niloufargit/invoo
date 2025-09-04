package com.invoo.orchestrator.domaine.entity.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequest {
    private String currentPassword;
    private String newPassword;
    private String confirmationPassword;
}
