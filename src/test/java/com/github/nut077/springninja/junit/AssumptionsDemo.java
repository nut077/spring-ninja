package com.github.nut077.springninja.junit;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumptionsDemo implements CommonTest {

    @Test
    void trueAssumption() {
        assumeTrue(5 > 4);
        assertEquals(5 + 5, 10);
    }

    @Test
    void assumptionThat() {
        boolean isMobile = true;
        int mobileVersion = 3;
        String appVersion = "1.2.3";

        assumingThat(
                isMobile,
                () -> assertAll(
                        () -> assertEquals(3, mobileVersion),
                        () -> assertEquals("1.2.3", appVersion)
                )
        );
    }
}
