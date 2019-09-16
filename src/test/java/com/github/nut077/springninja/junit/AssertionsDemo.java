package com.github.nut077.springninja.junit;

import org.junit.jupiter.api.Test;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.*;

public class AssertionsDemo implements CommonTest {

    @Test
    void standardAssertions() {
        assertTrue(5  > 4);
        assertEquals(5, 5);
        assertEquals(4, 4, "a failure message");
    }

    @Test
    void groupedAssertions() {
        String productName = "Apple"; // mock
        double productScore = 55.5; // mock
        assertAll("product",
                () -> assertEquals("Apple", productName),
                () -> assertEquals(55.5, productScore));
    }

    @Test
    void assertEqualsExceptionMessage() {
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> {
           throw new UnsupportedOperationException("Not supported");
        });
        assertEquals(exception.getMessage(), "Not supported");
    }

    @Test
    void assertThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Integer.valueOf(null), "Exception must thrown");
    }

    @Test
    void timeoutExceeded() {
        assertTimeout(ofMillis(10), () -> Thread.sleep(1));
    }

    @Test
    void timeoutExceededWithPreemptiveTermination() {
        assertTimeoutPreemptively(ofMillis(40), () -> Thread.sleep(1));
    }
}
