package org.edutecno.backend.auth.service;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import org.edutecno.backend.usuario.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;


    public String generateToken(final User user) {
        return buildToken(user, refreshExpiration);
    }

    public String generateRefreshToken(final User user) {
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final User user, final long expiration) {
        return Jwts
                .builder()
                .id(user.getId().toString())
                .claims(Map.of(
                        "name", user.getName(),
                        "role", user.getRole().name()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    private SecretKey getSignInKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(final String token) {
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();

    }

    public boolean isTokenValid(final String token, final User user) {
        final String username = extractUsername(token);
        return(username.equals(user.getEmail())) && !isTokenExpired(token);


    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
    final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    return jwtToken.getExpiration();

    }


}
