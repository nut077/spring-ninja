package com.github.nut077.springninja.entity;

import com.github.nut077.springninja.dto.Pojo;
import lombok.*;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Stream;

@Entity(name = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@SequenceGenerator(name = "products_seq")
@Table(indexes = {
        @Index(name = "products_idx_code_unique", columnList = "code", unique = true),
        @Index(name = "products_idx_status", columnList = "status")
})
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "Product.fetchDetailNotNull",
                query = "select p from products p where p.detail is not null"),
        @NamedQuery(name = "Product.fetchDetailLengthGreaterThan2",
                query = "select p from products p where length(p.detail) > 2")
})
@SqlResultSetMapping(
        name = "pojo",
        classes = {
                @ConstructorResult(
                        targetClass = Pojo.class,
                        columns = {
                                @ColumnResult(name = "id", type = Long.class),
                                @ColumnResult(name = "codeAndName", type = String.class),
                                @ColumnResult(name = "detail", type = String.class)
                        }
                )
        }
)
@NamedNativeQueries({
        @NamedNativeQuery(name = "Product.customFetchProductToPojo",
                query = "select p.id,p.code||' : '||p.name as codeAndName, p.detail from products p",
                resultSetMapping = "pojo")
})
//@Immutable ทำให้ class นี้ไม่สามารถอัพเดทได้ นอกจาก native sql
public class Product extends Common {

    /*public Product(String code, String name, Status status) {
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public Product(String code, String name, Status status, String detail) {
        this.code = code;
        this.name = name;
        this.status = status;
        this.detail = detail;
    }*/

    @Id
    @GeneratedValue(generator = "products_seq")
    private Long id;
    private String code;
    private String name;
    private String detail;
    private double score;
    private double price;


    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "products_alias_name", joinColumns = @JoinColumn(name = "products_id_custom"))
    @Column(length = 50)
    private Set<String> aliasName = new HashSet<>();

    @Column(length = 1)
    private Status status;

    @RequiredArgsConstructor
    public enum Status {
        APPROVED("A"),
        NOT_APPROVED("N"),
        DELETED("D"),
        PENDING("P");

        @Getter
        private final String code;

        public static Product.Status toStatus(String code) {
            return Stream.of(Product.Status.values()).parallel()
                    .filter(status -> status.getCode().equalsIgnoreCase(code))
                    .findAny().orElseThrow(() -> new IllegalArgumentException("The code : " + code + " is illegal argument."));
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Product.class.getSimpleName() + "[", "]")
                .add("id=" + id)
                .add("code='" + code + "'")
                .add("name='" + name + "'")
                .add("score='" + score + "'")
                .add("detail='" + detail + "'")
                .add("status=" + status)
                .add("create-date=" + getCreatedDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .toString();
    }


    // Tip ถ้าเราไม่ต้องการให้ field ไหนไปใช้ใน database มีวิธีให้ใช้ 3 วิธี
    /*@Transient
    private String excludeField1;

    private transient String excludeField2;

    private static String excludeField3;*/

}
