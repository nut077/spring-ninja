package com.github.nut077.springninja.hamcrest;

import com.github.nut077.springninja.entity.Product;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

@DisplayName("Asserts Demo Using Hamcrest")
public class HamcrestDemo {

    @Nested
    @DisplayName("Assert Objects")
    class AssertObjects {

        @Test
        @DisplayName("Should be null")
        void shouldBeNull() {
            MatcherAssert.assertThat(null, Matchers.nullValue());
        }

        @Test
        @DisplayName("Should not be null")
        void shouldNotBeNull() {
            MatcherAssert.assertThat(new Object(), Matchers.notNullValue());
        }
    }

    @Nested
    @DisplayName("Assert String")
    class AssertString {

        @Test
        @DisplayName("Should be empty string")
        void shouldBeEmptyString() {
            String str = "";
            MatcherAssert.assertThat(str, Matchers.emptyString());
        }

        @Test
        @DisplayName("Should be empty or null string")
        void shouldBeEmptyOrNullString() {
            String str = null;
            MatcherAssert.assertThat(str, Matchers.emptyOrNullString());
        }

        @Test
        @DisplayName("Should be equals ignore case")
        void shouldBeEqualsIgnoreCase() {
            String a = "abc";
            String b = "ABC";
            MatcherAssert.assertThat(a, Matchers.equalToIgnoringCase(b));
        }
    }

    @Nested
    @DisplayName("Assert Integers")
    class AssertInteger {

        @Test
        @DisplayName("Should be equals")
        void shouldBeEquals() {
            MatcherAssert.assertThat(5, Matchers.is(5));
        }

        @Test
        @DisplayName("Should not be equals")
        void shouldNotBeEquals() {
            MatcherAssert.assertThat(5, Matchers.not(4));
        }

        @Test
        @DisplayName("Should correct value")
        void shouldCorrectValue() {
            MatcherAssert.assertThat(1.5, Matchers.lessThan(2.0));
            MatcherAssert.assertThat(1.5, Matchers.lessThanOrEqualTo(2.0));
            MatcherAssert.assertThat(1.5, Matchers.closeTo(1, 2));
        }
    }

    @Nested
    @DisplayName("Assert References")
    class AssertReferences {

        @Test
        @DisplayName("Should reference to the same object")
        void shouldReferenceToSameObject() {
            Object acture = new Object();
            Object expected = acture;
            MatcherAssert.assertThat(acture, Matchers.sameInstance(expected));
        }

        @Test
        @DisplayName("Should not reference to the same object")
        void shouldNotReferenceToSameObject() {
            Object acture = new Object();
            Object expected = new Object();
            MatcherAssert.assertThat(acture, Matchers.not(Matchers.sameInstance(expected)));
        }
    }

    @Nested
    @DisplayName("Assert Collection")
    class AssertCollection {

        List<Integer> list;

        @BeforeEach
        void init() {
            list = new LinkedList<>();
        }

        @Test
        @DisplayName("Should empty")
        void shouldEmpty() {
            MatcherAssert.assertThat(list, Matchers.empty());
        }

        @Test
        @DisplayName("Should contain two elements")
        void shouldContainTwoElements() {
            list = Arrays.asList(1, 2);
            MatcherAssert.assertThat(list, Matchers.hasSize(2));
        }

        @Test
        @DisplayName("Should contain the correct elements in the given order")
        void shouldContainCorrectElementsInGivenOrder() {
            list = Arrays.asList(3, 4);
            MatcherAssert.assertThat(list, Matchers.contains(3, 4));
        }

        @Test
        @DisplayName("Should contain the correct elements in any order")
        void shouldContainCorrectElementsInAnyOrder() {
            list = Arrays.asList(5, 4, 3);
            MatcherAssert.assertThat(list, Matchers.containsInAnyOrder(5, 3, 4));
        }

        @Test
        @DisplayName("Should contain a correct element")
        void shouldContainCorrectElement() {
            list = Arrays.asList(5, 4, 3);
            MatcherAssert.assertThat(list, Matchers.hasItem(3));
        }

        @Test
        @DisplayName("Should not contain an incorrect element")
        void shouldNotContainIncorrectElement() {
            list = Arrays.asList(5, 4, 3);
            MatcherAssert.assertThat(list, Matchers.not(Matchers.hasItem(1)));
        }
    }

    @Nested
    @DisplayName("Assert Map")
    class AssertMap {

        Map<String, String> map;

        @BeforeEach
        void init() {
            map = new HashMap<>();
            map.put("k1", "v1");
        }

        @Test
        @DisplayName("Should contain the correct key")
        void shouldContainCorrectKey() {
            MatcherAssert.assertThat(map, Matchers.hasKey("k1"));
        }

        @Test
        @DisplayName("Should not contain the incorrect key")
        void shouldNotContainIncorrectKey() {
            MatcherAssert.assertThat(map, Matchers.not(Matchers.hasKey("k2")));
        }

        @Test
        @DisplayName("Should contain the correct value")
        void shouldContainCorrectValue() {
            MatcherAssert.assertThat(map, Matchers.hasEntry("k1", "v1")); // key และ value เหมือนกัน
        }

        @Test
        @DisplayName("Combine multiple assertions")
        void shouldCorrectAsserts() {
            MatcherAssert.assertThat(map, Matchers.allOf(
                    Matchers.hasKey("k1"),
                    Matchers.not(Matchers.hasKey("k2")),
                    Matchers.hasEntry("k1", "v1")
            ));
        }

        @Nested
        @DisplayName("Asserts Model")
        class AssertModel {

            private Product product;

            @BeforeEach
            void init() {
                product = new Product();
                product.setCode("p1");
                product.setName("apple");
                product.setPrice(100);
            }

            @Test
            @DisplayName("Should have correct code and name")
            void shouldHaveCorrectCodeAndName() {
                // allOf เหมือนกับ and  anyOf เหมือนกับ or คืออย่างใดอย่างหนึ่งถูกก็ได้
                MatcherAssert.assertThat("The product code must be 'p1' and name must be 'apple' or 'lemon'",
                        product, Matchers.allOf(
                                Matchers.hasProperty("code", Matchers.is("p1")),
                                Matchers.anyOf(
                                        Matchers.hasProperty("name", Matchers.is("apple")),
                                        Matchers.hasProperty("name", Matchers.is("lemon"))
                                )
                        ));
            }
        }

        @Nested
        @DisplayName("Asserts Regex")
        class AssertsRegex {

            @Test
            @DisplayName("Should correct email")
            void shouldCorrectEmailMatcher() {
                String email = "nuttapon@email.com";
                MatcherAssert.assertThat(email, EmailMatcher.isEmail());
            }
        }
    }
}
