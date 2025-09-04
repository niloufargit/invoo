package com.invoo.orchestrator.exposition.api;

import com.invoo.orchestrator.domaine.entity.dto.auth.ChangePasswordRequest;
import com.invoo.orchestrator.domaine.entity.User;
import com.invoo.orchestrator.domaine.entity.dto.user.UpdateUser;
import com.invoo.orchestrator.domaine.entity.dto.user.UserRequest;
import com.invoo.orchestrator.domaine.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PatchMapping("/changepswd")
    public ResponseEntity<?> changePasswordOld(
          @RequestBody UserRequest request,
          Principal connectedUser
    ) {
        service.changePasswordOld(request, connectedUser);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal User authenticatedUser) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", authenticatedUser.getId());
        userInfo.put("firstname", authenticatedUser.getFirstname());
        userInfo.put("lastname", authenticatedUser.getLastname());
        userInfo.put("email", authenticatedUser.getEmail());
        return userInfo;
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            Principal connectedUser
    ) {
        return service.changePassword(request, connectedUser);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> changePassword(@PathVariable UUID id, @Valid @RequestBody UpdateUser updatedUser) {
        return service.updateUser(id,updatedUser);


    }

}
