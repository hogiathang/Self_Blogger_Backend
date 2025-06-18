package com.techblog.backend.configuration;

import com.techblog.backend.service.publicInterface.IJWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Set;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {
    private final IJWTService jwtService;
    private final Logger logger = LoggerFactory.getLogger(UserAuthenticationFilter.class);
    private static final Set<String> PUBLIC_ENDPOINTS = Set.of(
            "/",
            "/api/v1/auth/**",
            "/api/v1/search/**",
            "/v3/api-docs/**",
            "/swagger-ui/**"
    );

    public UserAuthenticationFilter(IJWTService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String requestURI = request.getRequestURI();
            String method = request.getMethod();

            if ("GET".equalsIgnoreCase(method) && requestURI.startsWith("/api/v1/images/")) {
                filterChain.doFilter(request, response);
                return;
            }

            for (String endPoint : PUBLIC_ENDPOINTS) {
                if (endPoint.endsWith("/**")) {
                    endPoint = endPoint.substring(0, endPoint.length() - 3);
                    if (requestURI.startsWith(endPoint)) {
                        filterChain.doFilter(request, response);
                        return;
                    }
                } else if (requestURI.equals(endPoint)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }


            Enumeration<String> cookies = request.getHeaders("Cookie");
            StringBuilder accessToken = new StringBuilder();
            StringBuilder refreshToken = new StringBuilder();

            cookies.asIterator()
                    .forEachRemaining(cookie -> {
                        String[] tokens = cookie.split("; ");
                        for (String token: tokens) {
                            if (token.startsWith("accessToken=")) {
                                accessToken.append(token.substring("accessToken=".length()));
                            }
                            if (token.startsWith("refreshToken=")){
                                refreshToken.append(token.substring("refreshToken=".length()));
                            }
                        }
                    });

            if (!accessToken.isEmpty() && !refreshToken.isEmpty()) {
                if (jwtService.isValidToken(accessToken.toString())) {
                    SecurityContextHolder.getContext().setAuthentication(
                            jwtService.getAuthenticationFromToken(accessToken.toString())
                    );
                    filterChain.doFilter(request, response);
                } else if (jwtService.isValidToken(refreshToken.toString())) {
                    String newAccessToken = jwtService.generateToken(
                            jwtService.getUsernameFromToken(refreshToken.toString()),
                            jwtService.getRoleFromToken(refreshToken.toString())
                    );
                    ResponseCookie newAccessCookie = ResponseCookie.from("accessToken", newAccessToken)
                            .httpOnly(true)
                            .secure(true)
                            .path("/")
                            .maxAge(60 * 60)
                            .build();

                    response.addHeader("Set-Cookie", newAccessCookie.toString());
                    SecurityContextHolder.getContext().setAuthentication(
                            jwtService.getAuthenticationFromToken(newAccessToken)
                    );
                    filterChain.doFilter(request, response);
                } else {
                    throw new ServletException("Invalid tokens");
                }
            } else {
                throw new ServletException("No tokens found in request headers");
            }
        } catch (Exception e) {
            logger.error("Error during authentication filter processing: {}", e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
