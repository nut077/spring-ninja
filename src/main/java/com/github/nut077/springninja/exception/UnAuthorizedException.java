package com.github.nut077.springninja.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class UnAuthorizedException extends CommonException {

    public UnAuthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, "401", message);
    }
}
