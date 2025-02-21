package com.example.task.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;


@Getter
@Builder
public class ErrorResponse {

    @Builder.Default
    private LocalDateTime time = LocalDateTime.now();

    private HttpStatus httpStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String message;

    @Builder.Default
    private final Boolean isSuccess = false;


}
