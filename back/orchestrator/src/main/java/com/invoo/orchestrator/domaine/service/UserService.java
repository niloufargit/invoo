package com.invoo.orchestrator.domaine.service;

import com.invoo.orchestrator.domaine.entity.dto.auth.ChangePasswordRequest;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.domaine.entity.User;
import com.invoo.orchestrator.domaine.entity.dto.user.UpdateUser;
import com.invoo.orchestrator.domaine.entity.dto.user.UserRequest;
import com.invoo.orchestrator.domaine.repository.IUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final IUserRepository repository;

    @Deprecated
    public void changePasswordOld(UserRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new IllegalStateException("Wrong password");
        }
        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            throw new IllegalStateException("Password are not the same");
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        repository.save(user);
    }

    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public ResponseEntity<?> changePassword(@Valid ChangePasswordRequest request, Principal connectedUser) {

        var user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        // check if the current password is correct
        if (!passwordEncoder.matches(
                request.currentPassword(),
                user.getPassword()
        )) {
            ApplicationExceptions.genericException( "Wrong current password", HttpStatus.BAD_REQUEST);
        }
        // check if the two new passwords are the same
        if (!request.newPassword().equals(request.confirmNewPassword())) {
            ApplicationExceptions.genericException( "New and confirm password are wrong", HttpStatus.BAD_REQUEST);
        }

        // update the password
        user.setPassword(passwordEncoder.encode(request.newPassword()));

        // save the new password
        repository.save(user);
        return ResponseEntity.ok().body( true );
    }

    public ResponseEntity<?> updateUser(UUID id, UpdateUser updatedUser) {
        var user = repository.findById(id);
        if (user.isEmpty()) ApplicationExceptions.genericException("User dont exist", HttpStatus.BAD_REQUEST);
        repository.updateUserById(id, updatedUser.getFirstname(), updatedUser.getLastname(), updatedUser.getEmail());
        return ResponseEntity.ok().build();
    }
}
