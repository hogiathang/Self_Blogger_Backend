package com.techblog.backend.utils;

import com.techblog.backend.dto.utils.TokenUtils;
import com.techblog.backend.service.authentication.JWTService;
import com.techblog.backend.service.publicInterface.jwt.IJWTService;
import org.springframework.http.ResponseCookie;

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
    public static TokenUtils getTokenResponseEntity(String username) {
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

        return new TokenUtils(
                accessCookie,
                refreshCookie
        );
    }

    /**
     * Phương thức này dùng để trả về giá trị, giá trị cũ o1, giá trị mới o2.
     * @param o1 Giá trị cũ
     * @param o2 Giá trị mới
     * @return Giá trị mới nếu o1 không null, nếu o1 null thì trả về o2, nếu o2 null thì trả về o1.
     */
    public static Object setValue(Object o1, Object o2) {
        if (o2 == null) return o1;
        else return o2;
    }
}
