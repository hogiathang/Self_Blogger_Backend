package com.techblog.backend.dto.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Schema (
        name= "ErrorResponseDto",
        description = "Schema to hold error response information"
)
@Data
@NoArgsConstructor
public class ErrorResponseDto {
    @Schema(
            description = "API path"
    )
    private String apiPath;
    @Schema (
            description = "Error code"
    )
    private HttpStatus errorCode;

    @Schema (
            description = "Error message"
    )
    private String errorMessage;

    @Schema (
            description = "Error time", example = "2021-09-01T12:00:00Z"
    )
    private String errorTime;

    public ErrorResponseDto(
            String apiPath,
            HttpStatus status,
            String errorMessage,
            String errorTime
    ) {
        this.apiPath = apiPath;
        this.errorCode = status;
        this.errorMessage = errorMessage;
        this.errorTime = errorTime;
    }
}