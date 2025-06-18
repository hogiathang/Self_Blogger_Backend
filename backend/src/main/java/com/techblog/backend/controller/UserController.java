package com.techblog.backend.controller;

import com.techblog.backend.dto.LoginForm;
import com.techblog.backend.dto.RegisterForm;
import com.techblog.backend.dto.Token;
import com.techblog.backend.entity.user.UserEntity;
import com.techblog.backend.service.publicInterface.IUserService;
import com.techblog.backend.utils.Utils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Controller dùng cho việc xác thực người dùng
 * Tính năng: Đăng kí, đăng nhập người dùng. Chỉnh sửa thông tin cá nhân
 */
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "User Authentication", description = "Endpoints cho người dùng đăng kí, đăng nhập vào hệ thống")
public class UserController {

    private final IUserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Đăng kí người dùng mới
     */
    @Operation(
            summary = "Đăng kí người dùng mới",
            description = "Endpoint cho phép người dùng đăng kí tài khoản mới. Yêu cầu cung cấp tên người dùng, mật khẩu và mã giới thiệu (nếu có)."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Đăng kí thành công, trả về token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Yêu cầu không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi máy chủ nội bộ")
    })
    @PostMapping("/register")
    public ResponseEntity<Token> register(@RequestBody RegisterForm registerForm) {

        UserEntity newUser = new UserEntity(
                registerForm.getUsername(),
                passwordEncoder.encode(registerForm.getPassword()),
                registerForm.getRef(),
                "USER"
        );
        userService.addUser(newUser);
        Token token =  Utils.getTokenResponseEntity(newUser.getUsername());
        ResponseCookie accessCookie = ResponseCookie.from("accessToken", token.getAccessToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(60 * 60)
                .build();

        ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(86400)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers -> {
                    headers.add("Set-Cookie", accessCookie.toString());
                    headers.add("Set-Cookie", refreshCookie.toString());
                })
                .body(token);
    }

    /**
     * Đăng nhập người dùng
     */
    @Operation(
            summary = "Đăng nhập người dùng",
            description = "Endpoint cho phép người dùng đăng nhập vào hệ thống. Yêu cầu cung cấp tên người dùng và mật khẩu."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Đăng nhập thành công, trả về token"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Thông tin đăng nhập không hợp lệ"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Lỗi máy chủ nội bộ")
    })
    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody LoginForm loginForm) {
        try{
            if (userService.authenticate(loginForm.getUsername(), loginForm.getPassword())) {
                Token token = Utils.getTokenResponseEntity(loginForm.getUsername());
                ResponseCookie accessCookie = ResponseCookie.from("accessToken", token.getAccessToken())
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(60 * 60)
                        .build();

                ResponseCookie refreshCookie = ResponseCookie.from("refreshToken", token.getRefreshToken())
                        .httpOnly(true)
                        .secure(true)
                        .path("/")
                        .maxAge(86400)
                        .build();

                return ResponseEntity.status(HttpStatus.OK)
                        .headers(headers -> {
                            headers.add("Set-Cookie", accessCookie.toString());
                            headers.add("Set-Cookie", refreshCookie.toString());
                        })
                        .body(token);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}