package com.github.nut077.springninja.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.Duration;
import java.util.Arrays;

@Log4j2
@EnableCaching
@Configuration
public class ProductCacheConfig {

    @Bean
    public SimpleCacheManager buildSimpleCacheManager() {
        CaffeineCache productCache = buildCaffeineCache(CacheName.PRODUCT, 100);
        CaffeineCache productsCache = buildCaffeineCache(CacheName.PRODUCTS, 10);
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        simpleCacheManager.setCaches(Arrays.asList(productCache, productsCache));
        simpleCacheManager.initializeCaches();
        return simpleCacheManager;
    }

    private CaffeineCache buildCaffeineCache(String name, long maxSize) {
        log.info(() -> "Build CaffeineCache[" + name + "], maximumSize[" + maxSize + "]");
        return new CaffeineCache(name, Caffeine.newBuilder()
                .softValues() // เมื่อ memory ใกล้จะเต็ม gb ก็จะลบ cache ตัวแรกท้ายทิ้ง
                .maximumSize(maxSize) // ค่าสุงสุดที่จะเก็บ cache ไว้
                .expireAfterAccess(Duration.ofHours(1)) // กำหนด record นั้นใน cache ถูก expire หลังจากถูก access เมื่อไร
                .expireAfterWrite(Duration.ofHours(24)) // หลังจากที่ถูกเขียนค่าเข้า cache จะถูก expire เมื่อไร ต่อให้มีคน access มาเท่าไรเมื่อถึง 1 วันแล้วก็จะถูก expire ทิ้ง เพื่อทำการโหลดค่าขึ้นมาใหม่จาก database
                .build());
    }

    // ทุกๆเที่ยงคืนของทุกวัน
    @Scheduled(cron = "0 0 0 * * *")
    @Caching(evict = {
            @CacheEvict(cacheNames = CacheName.PRODUCT, allEntries = true),
            @CacheEvict(cacheNames = CacheName.PRODUCTS, allEntries = true)
    }) // ทำการเคลีย cache  allEntries คือเคลียทั้งหมด
    public void evictAll() {
        log.info(() -> "Cache evict all @Scheduled(cron = '0 0 0 * * *')");
    }

    public static class CacheName {
        public static final String PRODUCT = "PRODUCT";
        public static final String PRODUCTS = "PRODUCTS";
    }
}
