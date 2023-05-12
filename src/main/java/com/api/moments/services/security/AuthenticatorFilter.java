package com.api.moments.services.security;

import com.api.moments.services.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers",
        "Authorization, Origin, X-Requested-With, Content-Type, Accept, Access-Control-Request-Method, Access-Control-Request-Headers");
    response.setHeader("Access-Control-Max-Age", "3600");

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpStatus.OK.value());
      return;
    }

    if (request.getServletPath().contains("/api/v1/moments/auth")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("user/new-user")) {
      filterChain.doFilter(request, response);
      return;
    }

    if (request.getServletPath().contains("doodly")) {
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

    boolean isValidToken = false;

    try {
      isValidToken = jwtService.isValidToken(token);
    } catch (Exception e) {
      response.getWriter().write(e.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    if (isValidToken) {
      try {
        var userId = jwtService.getUserId(token.substring(7));
        var user = userService.getUserById(userId);

        var authentication = new UsernamePasswordAuthenticationToken(user, null, null);
        request.setAttribute("userId", user.getId());
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (Exception e) {
        response.getWriter().write(e.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        return;
      }
    } else {
      response.getWriter().write("Invalid token!");
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      return;
    }

    filterChain.doFilter(request, response);
  }

  //        try {
  //            var userId = jwtService.getUserId(token.substring(7));
  //            var user = userService.getUserById(userId);
  //
  //            if (user == null) {
  //                response.getWriter().write("User not found");
  //                response.setStatus(HttpStatus.UNAUTHORIZED.value());
  //                return;
  //            }
  //
  //            Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);
  //            SecurityContextHolder.getContext().setAuthentication(authentication);
  //            request.setAttribute("userId", user.getId());
  //            filterChain.doFilter(request, response);
  //
  //
  //        } catch (InvalidTokenException e) {
  //            response.getWriter().write("Invalid token");
  //            response.setStatus(HttpStatus.UNAUTHORIZED.value());
  //        }


}
