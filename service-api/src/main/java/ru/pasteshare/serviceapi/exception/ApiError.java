package ru.pasteshare.serviceapi.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ApiError {

    private int code;

    private HttpStatus status;

    private String message;
}
