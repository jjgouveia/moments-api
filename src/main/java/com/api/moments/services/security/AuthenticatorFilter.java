package com.api.moments.services.security;

import com.api.moments.services.user.UserService;
import com.api.moments.util.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class AuthenticatorFilter extends OncePerRequestFilter {
  @Autowired
  private IJwtService jwtService;

  @Autowired
  private UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    if (request.getServletPath().contains("/api/v1/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("/moments/users/new")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("swagger") || request.getServletPath().contains("docs")) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = request.getHeader("Authorization");

    if (token == null || !token.startsWith("Bearer ")) {
      response.getWriter().write("Expired or Invalid token");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    try {
      var userId = jwtService.getUserId(token.substring(7));
      var user = userService.getUserById(userId);

      if (user == null) {
        response.getWriter().write("User not found");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return;
      }

      Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
      SecurityContextHolder.getContext().setAuthentication(authentication);
      request.setAttribute("userId", user.getId());
      filterChain.doFilter(request, response);


    } catch (InvalidTokenException e) {
      response.getWriter().write("Invalid token");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

  }
}
