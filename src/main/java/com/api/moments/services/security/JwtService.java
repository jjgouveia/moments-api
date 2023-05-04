package com.api.moments.services.security;

import com.api.moments.util.InvalidTokenException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService implements IJwtService {

  @Value("${app.token.expiration}")
  private long expirationTime;

  @Value("${app.secret.key}")
  private String key;

  @Override
  public String generateToken(UUID userId) {
    return Jwts.builder().setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(generateKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key generateKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }

  public void isValidToken(String token) throws InvalidTokenException {
    String tokenizer = token.replace("Bearer ", "");

    try {
      var claims =
          Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(tokenizer)
              .getBody();

      var subject = claims.getSubject();
      var tExpiration = claims.getExpiration();
      var userId = UUID.fromString(subject);

      if (!userId.toString().equals(subject) || tExpiration.before(new Date())) {
        throw new InvalidTokenException("Invalid or expired token");
      }
    } catch (JwtException e) {
      throw new InvalidTokenException("Invalid token");
    }
  }

  @Override
  public UUID getUserId(String token) {
    String tokenizer = token.replace("Bearer ", "");
    try {
      var claims =
          Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(tokenizer)
              .getBody();
      var subject = claims.getSubject();
      return UUID.fromString(subject);
    } catch (JwtException | IllegalArgumentException e) {
      throw new InvalidTokenException("Invalid or expired token");
    }
  }

}
