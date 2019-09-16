package com.github.nut077.springninja.config;

import com.github.nut077.springninja.controller.interceptor.AccessTokenInterceptor;
import com.github.nut077.springninja.controller.interceptor.HelloInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final HelloInterceptor helloInterceptor;
    private final AccessTokenInterceptor accessTokenInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(helloInterceptor).addPathPatterns("/api/v1/products").order(2); // addPathPatterns ให้ทำเฉพาะ path ไหน order เป็นการเรียงลำดับว่าให้ interceptor ไหนทำก่อน
        registry.addInterceptor(accessTokenInterceptor).order(1);
    }
}
