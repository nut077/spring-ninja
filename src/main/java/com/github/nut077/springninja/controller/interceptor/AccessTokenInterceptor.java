package com.github.nut077.springninja.controller.interceptor;

import com.github.nut077.springninja.exception.UnAuthorizedException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

@Component
public class AccessTokenInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        return validate(request.getHeader("x-Access-Token"));
    }

    private boolean validate(String accessToken) {
        if (StringUtils.isEmpty(accessToken)) throw new UnAuthorizedException("Access-Token is empty.");

        // Mocked data
        List<String> list = Arrays.asList("27bb0a9c", "7432485b");
        if (!list.contains(accessToken)) throw new UnAuthorizedException("Invalid Access-Token");

        return true;
    }
}
