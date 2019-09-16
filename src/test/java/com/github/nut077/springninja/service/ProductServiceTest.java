package com.github.nut077.springninja.service;

import com.github.nut077.springninja.dto.ProductDto;
import com.github.nut077.springninja.dto.mapper.ProductMapper;
import com.github.nut077.springninja.dto.mapper.ProductMapperImpl;
import com.github.nut077.springninja.dto.mapper.SetMapper;
import com.github.nut077.springninja.entity.Product;
import com.github.nut077.springninja.junit.StopwatchExtension;
import com.github.nut077.springninja.repository.ProductRepository;
import info.solidsoft.mockito.java8.api.WithBDDMockito;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@DisplayName("Test product service")
@ExtendWith({StopwatchExtension.class, MockitoExtension.class})
@Execution(ExecutionMode.CONCURRENT)
class ProductServiceTest implements WithBDDMockito {

    private ProductService service;

    @Mock
    private ProductRepository repo;

    private ProductMapper productMapper;

    private SetMapper setMapper = Mappers.getMapper(SetMapper.class);


    @BeforeEach
    void beforeEach() {
        productMapper = new ProductMapperImpl(setMapper);
        service = new ProductService(repo, productMapper);
    }

    private Product gen(long id, String code, String name, double score, Product.Status status) {
        Product product = new Product();
        product.setId(id);
        product.setCode(code);
        product.setName(name);
        product.setScore(score);
        product.setStatus(status);
        return product;
    }

    @Test
    void should_SuccessToFindAll_When_StatusIsNull() {
        // given
        Product p1 = gen(1, "p100", "product-1", 10d, Product.Status.APPROVED);
        Product p2 = gen(2, "p200", "product-2", 20d, Product.Status.PENDING);
        List<Product> products = Arrays.asList(p1, p2);
        products.sort(Comparator.comparing(Product::getId));
        given(repo.findAll()).willReturn(products);

        // when
        List<ProductDto> actual = service.findAll(null);

        // then
        then(repo).should(times(1)).findAll();
        MatcherAssert.assertThat(actual, Matchers.hasSize(2));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("code", Matchers.is("p100")));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("status", Matchers.is("APPROVED")));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("code", Matchers.is("p200")));
        MatcherAssert.assertThat(actual.get(1), Matchers.hasProperty("status", Matchers.is("PENDING")));
    }


    @Test
    void should_SuccessToFindAll_When_StatusApprove() {
        // given
        Product p1 = gen(1, "p100", "product-1", 10d, Product.Status.APPROVED);
        given(repo.findAllByStatus(Product.Status.APPROVED)).willReturn(Arrays.asList(p1));

        // when
        List<ProductDto> actual = service.findAll(Product.Status.APPROVED);

        // then
        MatcherAssert.assertThat(actual, Matchers.hasSize(1));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("code", Matchers.is("p100")));
        MatcherAssert.assertThat(actual.get(0), Matchers.hasProperty("status", Matchers.is("APPROVED")));
    }


    @Test
    void should_SuccessToFind_When_IDIsExist() {
        // given
        Product p1 = gen(1, "p300", "product-3", 30d, Product.Status.DELETED);
        given(repo.findById(1L)).willReturn(Optional.of(p1));

        // when
        ProductDto actual = service.find(1L);

        // then
        MatcherAssert.assertThat(actual, Matchers.hasProperty("code", Matchers.is("p300")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("status", Matchers.is("DELETED")));
    }

    @Test
    void should_SuccessToSave_When_AssignValidValues() {
        // given
        ProductDto dto = new ProductDto();
        dto.setCode("p400");
        dto.setName("product-4");
        dto.setScore(40d);
        dto.setPrice(3d);
        dto.setStatus("PENDING");
        given(repo.saveAndFlush(any(Product.class))).willReturn(productMapper.map(dto));

        // when
        ProductDto actual = service.save(dto);

        // then
        MatcherAssert.assertThat(actual, Matchers.hasProperty("code", Matchers.is("p400")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("status", Matchers.is("PENDING")));
    }

    @Test
    void should_SuccessToReplace_When_AssignValidValues() {
        // given
        ProductDto dto = new ProductDto();
        dto.setCode("p500");
        dto.setName("product-5");
        dto.setScore(50d);
        dto.setPrice(5d);
        dto.setStatus("PENDING");

        Product entity = productMapper.map(dto);
        entity.setId(5L);
        given(repo.findById(5L)).willReturn(Optional.of(entity));
        given(repo.saveAndFlush(any(Product.class))).willReturn(entity);

        // when
        ProductDto actual = service.replace(5L, dto);

        // then
        MatcherAssert.assertThat(actual, Matchers.hasProperty("code", Matchers.is("p500")));
        MatcherAssert.assertThat(actual, Matchers.hasProperty("status", Matchers.is("PENDING")));
    }

    @Test
    void should_SuccessToUpdateScore_WhenIDIsExist() {
        // given
        given(repo.updateScore(anyLong(), anyDouble())).willReturn(1);

        // when
        Integer effected = service.updatedScore(1L, 100);

        // then
        MatcherAssert.assertThat(effected, Matchers.is(1));
    }

    @Test
    void should_SuccessToUpdateScore_WhenIDIsNotExist() {
        // given
        given(repo.updateScore(anyLong(), anyDouble())).willReturn(0);

        // when
        Integer effected = service.updatedScore(1L, 100);

        // then
        MatcherAssert.assertThat(effected, Matchers.is(0));
    }


    @Test
    void should_SuccessToDelete_AnyID() {
        // given
        willDoNothing().given(repo).deleteById(anyLong());

        // when
        service.delete(anyLong());

        // then
        then(repo).should(times(1)).deleteById(anyLong());
        then(repo).should(never()).deleteAll();
    }
}
