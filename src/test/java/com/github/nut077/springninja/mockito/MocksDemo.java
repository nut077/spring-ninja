package com.github.nut077.springninja.mockito;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
@DisplayName("Mockito demo")
public class MocksDemo {

    @Mock
    List<Integer> mock;

    @Spy
    List<Integer> spy = new ArrayList<>();

    @Test
    @DisplayName("BDD Mockito")
    void demo() {
        // Given
        // When
        // Then
    }

    @Test
    void mock_demo() {
        System.out.println("mock_demo");

        // Given == Mock
        given(mock.size()).willReturn(3);

        // When == Perform the test
        int size = mock.size();
        System.out.println("size : " + size);

        // Then == Assert
        then(mock).should().size();
        then(mock).should(never()).clear();
        Assertions.assertEquals(3, size);
    }

    @Test
    void spy_vs_mock_demo() {
        System.out.println("spy_vs_mock_demo");
        System.out.println("\nadded  to list");

        mock.add(1);
        spy.add(1);

        System.out.println("mock size : " + mock.size());
        System.out.println("spy size : " + spy.size());

        System.out.println("\nGiven size = 8");
        given(mock.size()).willReturn(8);
        given(spy.size()).willReturn(8);

        System.out.println("mock size : " + mock.size());
        System.out.println("spy size : " + spy.size());
    }

    @Test
    void mock_should_in_order_demo() {
        System.out.println("mock_should_in_order_demo");

        // Given
        // No Given

        // When
        mock.add(5);
        mock.add(3);
        mock.add(1);

        // Then
        // without InOrder
        then(mock).should().add(3);
        then(mock).should().add(5);
        then(mock).should().add(1);

        // with InOrder
        InOrder order = inOrder(mock);
        then(mock).should(order).add(5);
        then(mock).should(order).add(3);
        then(mock).should(order).add(1);
    }

    @Test
    void spy_void_demo() {
        System.out.println("spy_void_demo");

        // Given
        spy.add(1);
        System.out.println("spy size : " + spy.size()); // 1

        willDoNothing().given(spy).clear(); // spy จะไม่ทำ clear

        // When
        System.out.println("clear");
        spy.clear();

        // Then
        then(spy).should().clear();
        System.out.println("spy size : " + spy.size()); // 1
    }

    @Test
    void mock_exception_demo() {
        System.out.println("mock_exception_demo");

        // Given
        willThrow(new RuntimeException("Mock Exception")).given(mock).get(anyInt());

        // When
        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> mock.get(1));

        // Then
        Assertions.assertEquals(RuntimeException.class, throwable.getClass());
        Assertions.assertEquals("Mock Exception", throwable.getMessage());
        System.out.println(throwable.getClass());
        System.out.println(throwable.getMessage());
    }

    @Test
    void mock_chain() {
        System.out.println("mock_chain");

        // Given
        willDoNothing().willThrow(RuntimeException.class)
                .given(mock)
                .clear();

        // When
        mock.clear();

        Throwable throwable = Assertions.assertThrows(RuntimeException.class, () -> mock.clear());

        // Then
        then(mock).should(times(2)).clear();
        Assertions.assertEquals(RuntimeException.class, throwable.getClass());
    }
}
