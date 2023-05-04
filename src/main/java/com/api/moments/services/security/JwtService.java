package com.api.moments.services.security;

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
  private long EXPIRATION_TIME;

  @Value("${app.secret.key}")
  private String key;

  @Override
  public String generateToken(UUID userId) {
    return Jwts.builder().setSubject(userId.toString())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
        .signWith(generateKey(), SignatureAlgorithm.HS256).compact();
  }

  private Key generateKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }
}
