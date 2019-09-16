package com.github.nut077.springninja.junit;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

@Execution(ExecutionMode.CONCURRENT) // ทำให้ใช้หลาย Thread ในการ run
@ExtendWith(StopwatchExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // ทำให้ @BeforeAll @AfterAll ถูกเรียกใช้งาน
public interface CommonTest {

    @BeforeAll
    default void beforeAllTests() {
        System.out.println(String.format("[%s] Before all tests", Thread.currentThread().getName()));
    }

    @AfterAll
    default void afterAllTests() {
        System.out.println(String.format("[%s] After all tests", Thread.currentThread().getName()));
    }

    @BeforeEach
    default void beforeEachTest(TestInfo info) {
        System.out.println(String.format("[%s] Before execute [%s]", Thread.currentThread().getName(), info.getDisplayName()));
    }

    @AfterEach
    default void afterEachTest(TestInfo info) {
        System.out.println(String.format("[%s] Finished executing [%s]", Thread.currentThread().getName(), info.getDisplayName()));
    }
}
