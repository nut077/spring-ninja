package com.github.nut077.springninja.junit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

import java.util.concurrent.TimeUnit;

@DisplayName("Basic Tests demo")
class BasicTestDemo implements CommonTest {

    @Test
    @DisplayName("Should_Success_When_ProductIsValid")
    void test_method1(TestInfo info) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(String.format("[%s] Execute [%s]", Thread.currentThread().getName(), info.getDisplayName()));
    }

    @Test
    @Disabled // ไม่ใช้ test นี้
    @DisplayName("Should_ThrowException_When_ProductIsInvalid")
    void test_method2(TestInfo info) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(String.format("[%s] Execute [%s]", Thread.currentThread().getName(), info.getDisplayName()));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS) // ทำงานเฉพาะ Windows
    @EnabledOnJre(JRE.JAVA_8) // ทำงานเฉพาะ jre 8
    void test_method3(TestInfo info) throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(500);
        System.out.println(String.format("[%s] Execute [%s]", Thread.currentThread().getName(), info.getDisplayName()));
    }
}
