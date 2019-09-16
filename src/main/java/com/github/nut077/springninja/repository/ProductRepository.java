package com.github.nut077.springninja.repository;

import com.github.nut077.springninja.dto.Pojo;
import com.github.nut077.springninja.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface ProductRepository extends Common<Product, Long> {

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "Update com.github.nut077.springninja.entity.Product p set p.name = ?1 where p.code = ?2")
    int jpqlUpdate(String name, String code);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Query(value = "update products set name = ?1 where code = ?2", nativeQuery = true)
    int sqlUpdate(String name, String code);

    //Optional<Collection<Product>> findAllByStatus(Product.Status status);
    List<Product> findAllByStatus(Product.Status status);

    Optional<Collection<Product>> findAllByStatusOrderByIdDesc(Product.Status status);

    Optional<Collection<Product>> findAllByNameContaining(String name);

    Optional<Collection<Product>> findAllByNameContainingIgnoreCase(String name);

    Optional<Collection<Product>> findAllByCodeContainingAndNameEndingWith(String code, String name);

    Optional<Collection<Product>> findAllByCodeOrCodeAndName(String whereCode, String orCode, String andName);

    Optional<List<Product>> fetchDetailNotNull();

    Optional<List<Product>> fetchDetailLengthGreaterThan2();

    @Query(nativeQuery = true)
    List<Pojo> customFetchProductToPojo();

    // Indexing parameters
    @Query("select p from products p where p.name = ?1 and p.status = ?2")
    List<Product> selectAllByNameAndStatusByIndex(String name, Product.Status status);

    // Named parameters
    @Query("select p from products p where p.name = :name and p.status = :status")
    List<Product> selectAllByNameAndStatusByParam(@Param("name") String name, @Param("status") Product.Status status);

    // Named parameters
    @Query("select p from products p where p.name like %:name")
    List<Product> selectAllByNameEndWith(@Param("name") String name);

    @Query(nativeQuery = true,
            value = "select p.* from products p where p.status = :status and parsedatetime(p.created_date,'yyyy-MM-dd')  = :createdDate")
    List<Product> selectAllByStatusAndDate(@Param("status") String status, @Param("createdDate") String createdDate);

    // Named parameters
    @Modifying
    @Query("update products p set p.status = :status where p.code = :code")
    int updateStatusById(@Param("status") Product.Status status, @Param("code") String code);

    // Named parameters
    @Modifying
    @Query(nativeQuery = true,
            value = "delete from products p where p.status = :status")
    int removeAllByStatus(@Param("status") String status);

    Optional<Product> findByName(String name);

    @Modifying(clearAutomatically  = true) // clearAutomatically ถ้าเป็น true update แล้ว ให้ flush ลง database เลย
    @Query(value = "update products set score = :score where id = :id", nativeQuery = true)
    int updateScore(@Param("id") Long id, @Param("score") double score);
}