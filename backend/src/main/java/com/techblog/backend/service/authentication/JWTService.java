package com.techblog.backend.service.authentication;

import com.techblog.backend.service.publicInterface.IJWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
public class JWTService implements IJWTService {
    private static final String SIGNATURE = "your-very-secure-secret-key-should-be-long-enough";

    @Value("${expiration[0].access-token}")
    private long EXPIRATION_TIME;

    @Value("${expiration[1].refresh-token}")
    private long REFRESH_EXPIRATION_TIME;

    @Override
    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "access_token")
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String generateRefeshToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .claim("type", "refresh_token")
                .issuedAt(new Date())
                .expiration(Date.from(Instant.now().plus(REFRESH_EXPIRATION_TIME, ChronoUnit.MILLIS)))
                .signWith(getSigningKey(), Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            String role = claims.get("role", String.class);

            if (role == null) {
                throw new RuntimeException("Role not found in the token");
            }

            return role;

        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or role not found", e);
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            return claims.getSubject();
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid JWT token or username not found", e);
        }
    }

    @Override
    public Boolean isValidToken(String token) {
        try{
            JwtParser jwtParser = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build();


            boolean isTrueSignature = jwtParser.isSigned(token);

            Claims claims = jwtParser
                    .parseSignedClaims(token)
                    .getPayload();
            Date expirationDate = claims.getExpiration();
            boolean isNonExpired = expirationDate != null && expirationDate.after(new Date());

            return isTrueSignature && isNonExpired;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String resolveToken(HttpServletRequest request, String tokenName) {
        String token = null;
        if (request.getCookies() != null) {
            for (var cookie : request.getCookies()) {
                if (tokenName.equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SIGNATURE.getBytes(StandardCharsets.UTF_8));
    }
}