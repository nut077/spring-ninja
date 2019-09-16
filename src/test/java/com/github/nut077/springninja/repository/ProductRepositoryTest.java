package com.github.nut077.springninja.repository;

import com.github.nut077.springninja.entity.Product;
import com.github.nut077.springninja.junit.StopwatchExtension;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

@DisplayName("Test product repository")
@DataJpaTest // auto config h2 in memory
@ExtendWith(StopwatchExtension.class)
@Execution(ExecutionMode.CONCURRENT)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) ต่อกับ database จริงๆ
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    // alternative way สามารถใช้ save กรณีที่ตัว repo ไม่ได้ extends crud
    /*@Autowired
    private TestEntityManager manager;*/

    @Test
    @DisplayName("Should success when find all status approved")
    void findAllByStatusApproved() {
        System.out.println(String.format("[%s] findAllByStatusApproved", Thread.currentThread().getName()));

        // Given
        Product expected = new Product();
        expected.setCode("a01");
        expected.setName("apple");
        expected.setPrice(100);
        expected.setStatus(Product.Status.APPROVED);
        productRepository.save(expected);

        // alternative way
        //manager.persist(expected);

        // When
        List<Product> actual = productRepository.findAllByStatus(Product.Status.APPROVED);

        // Then
        MatcherAssert.assertThat(actual, Matchers.hasSize(1));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("code", Matchers.is("a01")));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("name", Matchers.is("apple")));
    }

    @Test
    @DisplayName("Should success when find all by status pending")
    void findAllByStatusPending() {
        System.out.println(String.format("[%s] findAllByStatusPending", Thread.currentThread().getName()));

        // Given
        Product expected = new Product();
        expected.setCode("a01");
        expected.setName("apple");
        expected.setPrice(100);
        expected.setStatus(Product.Status.APPROVED);
        productRepository.save(expected);

        // When
        List<Product> actual = productRepository.findAllByStatus(Product.Status.PENDING);

        // Then
        MatcherAssert.assertThat(actual, Matchers.hasSize(0));
        MatcherAssert.assertThat(actual, Matchers.empty());
    }

    @Test
    @DisplayName("Should success when updated score")
    void updateScore() {
        System.out.println(String.format("[%s] updated score", Thread.currentThread().getName()));

        // Given
        Product expected = new Product();
        expected.setCode("a01");
        expected.setName("apple");
        expected.setScore(100.00);
        expected.setStatus(Product.Status.APPROVED);
        productRepository.save(expected);

        // When
        int rows = productRepository.updateScore(expected.getId(), 60.00);
        Product actual = productRepository.findById(expected.getId()).orElse(new Product());

        // Then
        MatcherAssert.assertThat("Rows should be affected 1 row", rows, Matchers.is(1));
        MatcherAssert.assertThat("Score should be updated", actual.getScore(), Matchers.is(60.00));
    }

}