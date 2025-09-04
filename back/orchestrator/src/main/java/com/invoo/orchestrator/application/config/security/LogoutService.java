package com.invoo.orchestrator.application.config.security;

import com.invoo.orchestrator.domaine.repository.ITokenRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

  private final ITokenRepository tokenRepository;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {


      var jwt = Arrays.stream( request.getCookies() )
              .filter( cookie -> cookie.getName().equals( "jwt" ) )
              .map( Cookie::getValue).findAny();

      Cookie cookie = new Cookie("jwt", null);
      cookie.setMaxAge(0);
      //cookie.setSecure(true);
      cookie.setHttpOnly(true);
      cookie.setPath("/"); // Global
      response.addCookie(cookie);

      tokenRepository.killToken( jwt.get() );

      SecurityContextHolder.clearContext();
  }
}
