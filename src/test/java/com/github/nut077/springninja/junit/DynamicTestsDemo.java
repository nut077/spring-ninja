package com.github.nut077.springninja.junit;

import org.junit.jupiter.api.DynamicNode;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class DynamicTestsDemo implements CommonTest {

    @TestFactory
    Collection<DynamicTest> dynamicTestsFromCollection() {
        List<Integer> list = Arrays.asList(2, 4, 6, 8);
        List<DynamicTest> results = new ArrayList<>();
        list.forEach(i -> results.add(dynamicTest("Should_Success_When_Mod2EqualsZero_" + i
        , () -> assertEquals(0, i % 2, "Failure message"))));
        return results;
    }

    @TestFactory
    Stream<DynamicTest> dynamicTestsFromStream() {
        Stream<String> stream = Stream.of("Apple", "Lemon", "Coconut");
        return stream.map(name -> dynamicTest("Should_Success_When_NameLengthMoreThan4 : " + name
        , () -> assertTrue(name.length() >= 5)));
    }

    @TestFactory
    Stream<DynamicNode> dynamicTestsWithContainers() {
        IntStream stream = IntStream.of(500, 101);
        return stream.mapToObj(price -> dynamicContainer("Container_For_Tests_" + price,
                Stream.of(
                        dynamicTest("not null", () -> assertNotNull(price)),
                        dynamicContainer("Check amount", Stream.of(
                                dynamicTest("price >= 100", () -> assertTrue(price >= 100)),
                                dynamicTest("price <= 1000", () -> assertTrue(price <= 1000))
                        ))
                )));
    }
}
