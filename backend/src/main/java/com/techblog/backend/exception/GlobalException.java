package com.techblog.backend.exception;

import com.techblog.backend.dto.exception.ErrorResponseDto;
import com.techblog.backend.exception.all.IlegalArgumentException;
import com.techblog.backend.exception.all.NoContentException;
import com.techblog.backend.exception.user.AuthMethodNotSupportedException;
import com.techblog.backend.exception.user.UserAlreadyExistedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex,
                                                                  WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AuthMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleAuthMethodNotSupport(Exception ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.INTERNAL_SERVER_ERROR,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );

        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyExistedException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistedException(Exception ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(IlegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIlegalArgumentException(IlegalArgumentException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponseDto);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<ErrorResponseDto> handleNoContentException(NoContentException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest request) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                request.getDescription(false),
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                LocalDateTime.now().toString()
        );
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponseDto);
    }
}
