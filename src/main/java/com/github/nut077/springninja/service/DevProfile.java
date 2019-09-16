package com.github.nut077.springninja.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("dev")
@Service
public class DevProfile implements TestProfile {

    @Override
    public int calculate(int num1, int num2) {
        return num1 + num2;
    }
}
