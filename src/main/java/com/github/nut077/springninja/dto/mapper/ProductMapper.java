package com.github.nut077.springninja.dto.mapper;

import com.github.nut077.springninja.dto.ProductDto;
import com.github.nut077.springninja.entity.Product;
import org.mapstruct.*;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Mapper(componentModel = "spring",
        uses = SetMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface ProductMapper {

    @BeforeMapping
    default void beforeMapping(ProductDto productDto) {
        //Assert.isNull(productDto.getId(), () -> "Id must be null");
        Assert.hasText(productDto.getCode(), () -> "Code must has text");
        Assert.doesNotContain(productDto.getCode(), "xxx", "Code mustn't contain xxx");
        Assert.hasText(productDto.getName(), () -> "Name must has text");
        Assert.isTrue(productDto.getScore() > 0, () -> "Score must be positive");
        Assert.isTrue(productDto.getPrice() > 0, () -> "Price must be positive");
        Assert.isTrue(Stream.of(Product.Status.values()).parallel()
                .anyMatch(status -> status.name().equalsIgnoreCase(productDto.getStatus())), () -> "Status not found in an enum constant");
        Assert.isNull(productDto.getVersion(), () -> "Version must be null");
        Assert.isNull(productDto.getUpdatedDate(), () -> "Updated date must be null");
    }

    @AfterMapping
    default void afterMapping(Product entity, @MappingTarget ProductDto productDto) {
        System.out.println("afterMapping");
        productDto.setUpdatedDate(productDto.getUpdatedDate() != null ? productDto.getUpdatedDate() : entity.getCreatedDate());
    }

    /*@Mappings({
            @Mapping(source = "detail", target = "description"), // field detail ใน class Product เท่ากับ field description ใน class ProductDto
            //@Mapping(source = "price", target = "price", defaultValue = "1"), // กำหนดค่า default ของ price ให้เท่ากับ 1
            //@Mapping(target = "version", constant = "1") // ให้มีค่า 1 เสมอ
    })
    ProductDto map(Product entity);*/
    @Mapping(source = "detail", target = "description")
    ProductDto map(Product entity);

    List<ProductDto> map(Collection<Product> entity);

    @InheritInverseConfiguration
    //@Mapping(target = "score", ignore = true)
    Product map(ProductDto dto);

    List<Product> map(List<ProductDto> dto);
}
