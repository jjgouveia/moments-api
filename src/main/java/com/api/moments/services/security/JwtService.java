package com.api.moments.services.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService implements IJwtService {
  private static final String key =
      "546A576D5A7134743777217A25432A462D4A614E645267556B58703272357538";
  private final long EXPIRATION_TIME = 7200000;

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
