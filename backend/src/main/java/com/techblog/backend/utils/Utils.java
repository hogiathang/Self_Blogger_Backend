package com.techblog.backend.utils;

import com.techblog.backend.dto.Token;
import com.techblog.backend.service.authentication.JWTService;
import com.techblog.backend.service.publicInterface.IJWTService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;

/**
 * Utils class dùng để chứa các phương thức tiện ích chung cho toàn bộ ứng dụng.
 */
public class Utils {
    /**
     * Tạo ResponseEntity chứa Token cho người dùng
     *
     * @param username Tên người dùng để tạo token
     * @return ResponseEntity chứa Token và cookie
     */
    public static ResponseEntity<Token> getTokenResponseEntity(String username) {
        IJWTService jwtService = new JWTService();
        String accessToken = jwtService.generateToken(username, "USER");
        String refreshToken = jwtService.generateRefeshToken(username, "USER");

        ResponseCookie accessCookie = ResponseCookie.from("accessToken", accessToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(86400)
                .build();

        Token token = new Token(
                accessToken,
                refreshToken
        );
        return ResponseEntity.status(HttpStatus.OK)
                .headers(headers -> {
                    headers.add("Set-Cookie", accessCookie.toString());
                    headers.add("Set-Cookie", refreshCookie.toString());
                })
                .body(token);
    }
}
