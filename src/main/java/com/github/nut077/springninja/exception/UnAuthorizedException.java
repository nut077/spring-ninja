package com.github.nut077.springninja.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnAuthorizedException extends CommonException {

    private final HttpStatus status = HttpStatus.UNAUTHORIZED;
    private final String code = "401";

    public UnAuthorizedException(String message) {
        super(message);
    }
}
