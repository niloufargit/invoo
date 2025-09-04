package com.invoo.orchestrator.exposition.api;

import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.AuthenticationResponse;
import com.invoo.orchestrator.domaine.entity.dto.auth.ConfirmCodeResponse;
import com.invoo.orchestrator.domaine.entity.dto.auth.RegisterRequest;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPassword;
import com.invoo.orchestrator.domaine.entity.dto.auth.ResetPasswordToken;
import com.invoo.orchestrator.domaine.service.AuthenticationService;
import com.invoo.orchestrator.application.config.security.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

  private final AuthenticationService service;
  private final LogoutService logoutService;

  @PostMapping("/register")
  public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    return service.register(request);
  }

  @PostMapping("/authenticate")
  public ResponseEntity<AuthenticationResponse> authenticate(
          @Valid @RequestBody AuthenticationRequest request, HttpServletResponse response
  ) {
      log.info( "starting authenticate" );
      return ResponseEntity.ok(service.authenticate(request, response));
  }

    @GetMapping("/isconnected")
    public ResponseEntity<Boolean> isConnected(HttpServletRequest request, HttpServletResponse response) {
        return ResponseEntity.ok().body( service.isConnected(request, response) );
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Boolean> resetPassword(@Valid @RequestBody ResetPassword request) {
      return service.resetPassword(request);
    }

  @PostMapping("/confirm-account")
  public ResponseEntity<?> confirmAccount(
          @Valid @RequestBody ConfirmCodeResponse confirmCodeResponse,
          HttpServletResponse response
  ) {
    String token = confirmCodeResponse.token();
    return service.confirmAccount(token, response);
  }

  @GetMapping("/is-verified-account")
  public ResponseEntity<?> isVerifiedAccount(@RequestParam("email") String email, HttpServletResponse response) {
    return service.isVerifiedAccount(email, response);
  }

  @PostMapping("/reset-password/{token}")
  public ResponseEntity<Boolean> resetPasswordWithToken(
          @Valid @RequestBody ResetPasswordToken resetPasswordToken,
          @Valid @NotNull @NotBlank @PathVariable String token) {
      return service.resetPasswordToken(resetPasswordToken, token);
  }

  @PostMapping("/refresh-token")
  public void refreshToken(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws IOException {
    service.refreshToken(request, response);
  }

  @PostMapping("/success/logout")
  public ResponseEntity<?> logout(
          HttpServletRequest request,
          HttpServletResponse response,
          Authentication authentication
  ) {
    logoutService.logout(request, response, authentication);
    return ResponseEntity.ok().body(Map.of("message", "Logout successful"));
  }

}
