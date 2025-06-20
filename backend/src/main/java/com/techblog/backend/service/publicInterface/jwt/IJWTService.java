package com.techblog.backend.service.publicInterface.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface IJWTService {
    String  generateToken(String username, String role);
    String  generateRefeshToken(String username, String role);
    String  getRoleFromToken(String token);
    String  getUsernameFromToken(String token);
    Boolean isValidToken(String token);
    String  resolveToken(HttpServletRequest request, String tokenName);
    Authentication getAuthenticationFromToken(String token);
}
