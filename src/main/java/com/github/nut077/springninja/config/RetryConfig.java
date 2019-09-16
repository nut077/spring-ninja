package com.github.nut077.springninja.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.HashMap;
import java.util.Map;

@EnableRetry
@Configuration
public class RetryConfig {

    @Bean
    public RetryTemplate build() {
        RetryTemplate template = new RetryTemplate();
        Map<Class<? extends Throwable>, Boolean> exceptions = new HashMap<>();
        exceptions.put(RuntimeException.class, true); // true คือ retry
        exceptions.put(Exception.class, false); // false คือ not retry
        template.setRetryPolicy(new SimpleRetryPolicy(3, exceptions)); // จำนวนสูงสุดที่จะทำการ retry การเรียกใช้งาน method นับเป็น 1 ครั้ง เท่ากับว่า จะมีการ retry 2 ครั้ง

        ExponentialBackOffPolicy exponential = new ExponentialBackOffPolicy();
        exponential.setMultiplier(2);
        exponential.setInitialInterval(1000); // 1000ms = 1s
        exponential.setMaxInterval(60000); // 60000ms = 60s
        // การ retry รอบแรกจะทำหลังจาก 1000 * 2 = 2 วินาที รอบที่สอง 2000 * 2 = 4 วินาที คูณแบบนี้ไปเรื่อยๆ โดยมีค่าสูงสุดคือ 60 วิ
        template.setBackOffPolicy(exponential);

        /*
        การเรียกใช้อีกแบบ แต่ใช้แบบข้างบนดีกว่า
        FixedBackOffPolicy fixed = new FixedBackOffPolicy();
        fixed.setBackOffPeriod(1000);
        template.setBackOffPolicy(fixed);
        */

        return template;
    }
}
