package com.techblog.backend.utils;

import com.techblog.backend.dto.Token;
import com.techblog.backend.service.authentication.JWTService;
import com.techblog.backend.service.publicInterface.IJWTService;

/**
 * Utils class dùng để chứa các phương thức tiện ích chung cho toàn bộ ứng dụng.
 */
public class Utils {
    /**
     * Tạo ResponseEntity chứa Token cho người dùng
     *
     * @param username Tên người dùng để tạo token
     * @return Token chứa cookie
     */
    public static Token getTokenResponseEntity(String username) {
        IJWTService jwtService = new JWTService();
        String accessToken = jwtService.generateToken(username, "USER");
        String refreshToken = jwtService.generateRefeshToken(username, "USER");

        Token token = new Token(
                accessToken,
                refreshToken
        );
        return token;
    }
}
