package com.techblog.backend.dto.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseCookie;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenUtils {
    private ResponseCookie accessToken;
    private ResponseCookie refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn = 3600;

    public TokenUtils(ResponseCookie accessToken, ResponseCookie refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
