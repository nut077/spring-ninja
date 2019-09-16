package com.github.nut077.springninja.service;

import com.github.nut077.springninja.dto.ProductDto;
import com.github.nut077.springninja.dto.mapper.ProductMapper;
import com.github.nut077.springninja.entity.Product;
import com.github.nut077.springninja.exception.MandatoryException;
import com.github.nut077.springninja.exception.NotFoundException;
import com.github.nut077.springninja.exception.OptionalException;
import com.github.nut077.springninja.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.nut077.springninja.config.ProductCacheConfig.CacheName.PRODUCT;
import static com.github.nut077.springninja.config.ProductCacheConfig.CacheName.PRODUCTS;

@Log4j2
@RequiredArgsConstructor
@CacheConfig(cacheNames = PRODUCT) // ค่า default cache ที่จะเก็บ
@Service
public class ProductService {

    private final ProductRepository productRepository;
    //private final RetryTemplate retryTemplate;
    private final ProductMapper productMapper;

    @PostConstruct // จะทำงานหลังจาก class หรือ bean นั้นๆ ถูก initialize
    private void init() {
        log.info(this.getClass().getSimpleName() + ": init");
    }

    public Optional<Product> create(Product product) {
        return Optional.of(productRepository.saveAndFlush(product));
    }

    public Optional<Product> read(Long id) {
        return productRepository.findById(id);
    }

    @Cacheable(cacheNames = PRODUCTS)
    public List<ProductDto> findAll() {
        log.info("Connected to Database");
        return productMapper.map(productRepository.findAll());
    }

    @Cacheable(cacheNames = PRODUCTS) // ถ้าใส่ค่า cacheNames จะเก็บค่า cache ตามที่ใส่ไว้ ถ้าไม่ใส่จะเก็บ cache ตามที่ระบุไว้บน class ตรง @CacheConfig
    public List<ProductDto> findAll(Product.Status status) {
        log.info("Connected to Database");
        return productMapper.map((StringUtils.isEmpty(status)) ? productRepository.findAll() : productRepository.findAllByStatus(status));
    }

    @Cacheable(unless = "#result?.score < 50") // ไม่เก็บ cache ที่ score มีค่าน้อยกว่า 50
    public ProductDto find(Long id) {
        log.info("Connectd to Database");
        return productMapper.map(productRepository.findById(id).orElseThrow(()-> new NotFoundException("data not found")));
    }

    public Product findProductTemp(Long id) {
        log.info("Connectd to Database");
        return productRepository.findById(id).orElseThrow(()-> new NotFoundException("data not found"));
    }

    @CachePut(key = "#product.id") // update ค่า ใน cache โดยใช้ key คือ product id
    @CacheEvict(cacheNames = PRODUCTS, allEntries = true)
    public Optional<Product> update(Product product) {
        return Optional.ofNullable(productRepository.saveAndFlush(product));
    }

    @CachePut(key = "#product.id")
    @CacheEvict(cacheNames = PRODUCTS, allEntries = true)
    public ProductDto replace(Long id, ProductDto productDto) {
        find(id);
        return productMapper.map(productRepository.saveAndFlush(productMapper.map(productDto)));
    }

    @CachePut(key = "#product.id")
    @CacheEvict(cacheNames = PRODUCTS, allEntries = true)
    public Product save(Product product) {
        return productRepository.saveAndFlush(product);
    }

    public ProductDto save(ProductDto dto) {
        return productMapper.map(productRepository.saveAndFlush(productMapper.map(dto)));
    }


    @Transactional
    @Caching(evict = {
            @CacheEvict(cacheNames = PRODUCTS, allEntries = true),
            @CacheEvict(cacheNames = PRODUCT, key = "#id")
    })
    public int updatedScore(Long id, double score) {
        return productRepository.updateScore(id, score);
    }

    // ลบของภายใน cache
    @Caching(evict = {
            @CacheEvict(cacheNames = PRODUCTS, allEntries = true),
            @CacheEvict(cacheNames = PRODUCT, key = "#id")
    })
    @Retryable(
            include = {MandatoryException.class}, // exception ที่จะทำการ retry
            exclude = {OptionalException.class}, // exception ที่ไม่ทำการ retry
            maxAttempts = 3, // ทำการ retry กี่รอบ การเรียกใช้ method นับเป็บ 1 retry ครั้งแรกนับเป็น 2 retry ครั้งที่สองเป็น 3 หมายความว่ากถ้าเซตไว้ 3 จะทำการ retry 2 รอบ
            backoff = @Backoff(value = 1000, multiplier = 2) // retry จะทำงานหลังจาก ตอบแรก 1000 * 2 = 2 วิ ครั้งต่อมา retry 2000 * 2 = 4 วิ
    )
    public void delete(Long id) {
        log.info(() -> "Delete id " + id);
        productRepository.deleteById(id);
        //throw new MandatoryException("[MandatoryException]"); // fake exception
        //throw new OptionalException("[OptionalException]"); // fake exception
        //productRepository.deleteById(id);
    }

    // การเรียกใช้ Recover type ต้องเหมือนกัน อย่างเช่น ถ้า retry เป็น void Recover ต้องเป็น void ด้วย
    @Recover
    public void recover(MandatoryException e, Long id) {
        log.info("recover delete due to {}", e.getMessage());
        productRepository.deleteById(id);
    }

    @Recover
    public void recover(OptionalException e, Long id) {
        log.info("recover delete due to {}", e.getMessage());
        productRepository.deleteById(id);
    }

    // การทำ retry and recover โดยใช้ RetryTemplate จะทำงานใน retryTemplate.execute เท่านั้น โดยทำ config ไว้ที่ class RetryConfig
    /*public String templateRetry() {
        AtomicInteger i = new AtomicInteger(1);
        log.info("process other business logic");
        //...
        //...
        return retryTemplate.execute(
                context -> {
                    log.info("execute:: " + i.getAndIncrement() + " in normal process");
                    throw new MandatoryException(""); // fake exception
                    // throw new OptionalException(""); // fake exception
                    // return "completed from normal process";
                }, context -> {
                    log.info("execute:: " + i.getAndIncrement() + " in recover callback");
                    return "complete from recover callback";
                }
        );
    }*/

    @Async
    public CompletableFuture<Product> find(String name) throws InterruptedException {
        TimeUnit.SECONDS.sleep(1);
        log.info("[{}] Find product name[{}]", Thread.currentThread().getName(), name);
        return CompletableFuture.completedFuture(productRepository.findByName(name).orElse(null));
    }

    @Async
    public void voidMethod() {
        throw new RuntimeException("ทดสอบข้อผิดพลาด");
    }
}
