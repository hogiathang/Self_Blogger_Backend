package com.techblog.backend.dto;

public class Token {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private long expiresIn = 3600;

    public Token(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpiresIn() {
        return expiresIn;
    }
}
