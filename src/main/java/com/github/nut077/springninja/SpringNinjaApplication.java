package com.github.nut077.springninja;

import com.github.nut077.springninja.component.Calculator;
import com.github.nut077.springninja.config.ProductCacheConfig;
import com.github.nut077.springninja.config.property.AsyncProperty;
import com.github.nut077.springninja.config.property.CustomProperty;
import com.github.nut077.springninja.entity.Product;
import com.github.nut077.springninja.repository.OrderRepository;
import com.github.nut077.springninja.repository.ProductRepository;
import com.github.nut077.springninja.repository.specification.ProductSpecs;
import com.github.nut077.springninja.service.ProductService;
import com.github.nut077.springninja.service.TestProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.StopWatch;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Log4j2
@RequiredArgsConstructor
@SpringBootApplication
public class SpringNinjaApplication implements CommandLineRunner {

    /*private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final ProductCacheConfig productCacheConfig;
    private final SimpleCacheManager simpleCacheManager;
    private final AsyncProperty asyncProperty;
    //private final TestProfile testProfile;
    private final CustomProperty customProperty;

    private Calculator calculator;

    @Autowired
    @Qualifier("minus")
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }*/

    public static void main(String[] args) {
        SpringApplication.run(SpringNinjaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }

    /*@Override
    public void run(String... args) {
        //queryByExample();
        //queryByMethod();
        //nameNativeQuery();
        //queryAnnotation();
        //dynamicQuery();
        //cache();
        //asynchronous();
        //retryAndRecover();
        //asyncProperties();
        //profile();
        //profile2();
        beanAndQualifier();
        *//*log.info("COUNT : " + productRepository.count());
        Product product = new Product();
        product.setCode("P001");
        product.setName("Coffee");
        product.setStatus(Product.Status.APPROVED);
        product.getAliasName().add("Java");
        product.getAliasName().add("Cuppa");
        product.getAliasName().add("Caffeine");
        product = productRepository.save(product);
        log.info("COUNT : " + productRepository.count());
        log.info("INSERT : " + product);

        product.setName("TEA");
        product.setDetail("UPDATE DETAIL");
        productRepository.save(product);
        log.info("COUNT : " + productRepository.count());
        log.info("UPDATE : " + product);

        product = productRepository.findById(1L).orElse(new Product());
        log.info("COUNT : " + productRepository.count());
        log.info("READ : " + product);

        productRepository.delete(product);
        log.info("DELETE : " + productRepository.findAll());
        log.info("COUNT : " + productRepository.count());*//*


        *//*Product product1 = new Product();
        product1.setCode("1001");
        product1.setName("Coffee");
        product1.setStatus(Product.Status.APPROVED);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setCode("1002");
        product2.setName("Milk");
        product2.setStatus(Product.Status.PENDING);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setCode("1003");
        product3.setName("Beer");
        product3.setStatus(Product.Status.NOT_APPROVED);
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setCode("1004");
        product4.setName("Soda");
        productRepository.save(product4);

        productRepository.findAll().forEach(product ->
                System.out.println(product.getName() + "\tStatus :\t" + product.getStatus()));*//*

        *//*Product p1 = new Product();
        p1.setName("AAA");
        p1.setCode("01");
        p1.setStatus(Product.Status.APPROVED);
        p1 = productRepository.save(p1);
        p1.setDetail("abc");
        productRepository.save(p1);

        Order o1 = new Order();
        o1.setOrderId(new OrderId(1L, 1L));
        o1.setQuantity(2);
        o1 = orderRepository.save(o1);
        o1.setQuantity(3);
        orderRepository.save(o1);*//*


        // INSERT
        *//*log.info(() -> "BEGIN INSERT");
        Product p = new Product();
        p.setCode("C01");
        p.setName("INSERT");
        p = repo.saveAndFlush(p);
        log.info(() -> "AFTER INSERT : " + repo.findAll());

        // UPDATE BY JPA METHOD
        log.info(() -> "BEGIN UPDATE BY JPA METHOD");
        p.setName("JPA METHOD");
        repo.saveAndFlush(p);
        log.info(() -> "AFTER UPDATE BY JPA METHOD : " + repo.findAll());


        // UPDATE BY JPQL
        log.info(() -> "BEGIN UPDATE BY JPQL");
        try {
            repo.jpqlUpdate("JPQL", "C01");
            log.info(() -> "AFTER UPDATE BY JPQL : " + repo.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // UPDATE BY SQL
        log.info(() -> "BEGIN UPDATE BY SQL");
        try {
            repo.sqlUpdate("SQL", "C01");
            log.info(() -> "AFTER UPDATE BY SQL : " + repo.findAll());
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        log.info("END");*//*

    }

    private void queryByExample() {
        IntStream.range(0, 10).forEach(i -> info(" "));
        info("Inseting multiple Products");

        saveProduct();

        info("Count number of All Products : " + productRepository.count());
        productRepository.findAll().forEach(System.out::println);

        info("Find all 'APPROVED' Products ");
        Product probe1 = Product.builder().status(Product.Status.APPROVED).build();                       // Create Probe

        ExampleMatcher matcher1 = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("version");
        Example<Product> example1 = Example.of(probe1, matcher1);
        productRepository.findAll(example1).forEach(System.out::println);

        info("Find all Products that name contains 'a or A' ");
        Product probe2 = Product.builder().name("a").build();                         // Create Probe

        ExampleMatcher matcher2 = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("version")
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase();
        Example<Product> example2 = Example.of(probe2, matcher2);
        productRepository.findAll(example2).forEach(System.out::println);

        info("Find all Products that code contains '0' and name startsWith 'b' ");
        Product probe3 = Product.builder().code("0").name("b").build();                            // Create Probe

        ExampleMatcher matcher3 = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("version")
                .withMatcher("code", ExampleMatcher.GenericPropertyMatcher::contains)
                .withMatcher("name", ExampleMatcher.GenericPropertyMatcher::startsWith);
        Example<Product> example3 = Example.of(probe3, matcher3);
        productRepository.findAll(example3).forEach(System.out::println);
    }

    private void info(String message) {
        log.info(() -> message);
    }

    private void queryByMethod() {
        saveProduct();
        sout("Find all 'APPROVED' Products");
        productRepository.findAllByStatus(Product.Status.APPROVED).forEach(System.out::println);

        sout("Find all 'APPROVED' Products order by Id desc");
        productRepository.findAllByStatusOrderByIdDesc(Product.Status.APPROVED).get().forEach(System.out::println);

        sout("Find all Products that name contains 'b' ");
        productRepository.findAllByNameContaining("b").get().forEach(System.out::println);

        sout("Find all Products that name contains 'b or B' ");
        productRepository.findAllByNameContainingIgnoreCase("b").get().forEach(System.out::println);

        sout("Find all Products that code contains '0' and name endsWith '2' ");
        productRepository.findAllByCodeContainingAndNameEndingWith("0", "2").get().forEach(System.out::println);

        sout("Find all Products that code equals('101') or (code equals('103') and name equals('B1') ");
        productRepository.findAllByCodeOrCodeAndName("101", "103", "B1").get().forEach(System.out::println);
    }

    private void nameNativeQuery() {
        saveProduct();
        sout("Fetch Product Where Detail Not Null");
        productRepository.fetchDetailNotNull().get().forEach(System.out::println);

        sout("Fetch Product Where Detail Length > 2");
        productRepository.fetchDetailLengthGreaterThan2().get().forEach(System.out::println);

        sout("Custom Fetch Product to Pojo");
        productRepository.customFetchProductToPojo().forEach(System.out::println);
    }

    private void queryAnnotation() {
        saveProduct();
        sout("Count number of All Products : " + productRepository.count());
        productRepository.findAll().forEach(System.out::println);

        sout("[JPQL-Indexing parameters] Select all by name and status");
        productRepository.selectAllByNameAndStatusByIndex("C1", Product.Status.PENDING)
                .forEach(System.out::println);

        sout("{JPQL-Named parameters] Select all by name and status");
        productRepository.selectAllByNameAndStatusByParam("A1", Product.Status.APPROVED)
                .forEach(System.out::println);

        sout("[JPQL-Named parameters] Select all by name endwith");
        productRepository.selectAllByNameEndWith("2")
                .forEach(System.out::println);

        sout("[SQL-Named parameters] Select all by status and date");
        productRepository.selectAllByStatusAndDate(Product.Status.APPROVED.getCode(), DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()))
                .forEach(System.out::println);

        sout(productRepository.updateStatusById(Product.Status.DELETED, "104"));

        sout(productRepository.findById(4L).toString());

        sout(productRepository.removeAllByStatus(Product.Status.DELETED.getCode()));

        sout(productRepository.findById(4L).toString());

    }

    private void saveProduct() {
        productRepository.saveAll(Arrays.asList(
                Product.builder().code("101").name("A1A").status(Product.Status.APPROVED).build(),
                Product.builder().code("102").name("B2B").status(Product.Status.APPROVED).detail("Hi").build(),
                Product.builder().code("103").name("C1C").status(Product.Status.PENDING).build(),
                Product.builder().code("104").name("D2D").status(Product.Status.NOT_APPROVED).detail("Hello").build()
        ));
    }

    private void sout(Serializable message) {
        IntStream.range(0, 3).forEach(i -> System.out.println());
        System.out.println(message);
    }

    private void dynamicQuery() {
        saveProduct();
        sout("Count number of All Products : " + productRepository.count());
        productRepository.findAll().forEach(System.out::println);

        sout("Name Equals 'C1C'");
        productRepository.findAll(ProductSpecs.nameEquals("C1C"))
                .forEach(System.out::println);

        sout("Name like '2'");
        productRepository.findAll(ProductSpecs.nameLike("2"))
                .forEach(System.out::println);

        sout("Status Equals 'PENDING'");
        productRepository.findAll(ProductSpecs.statusEquals(Product.Status.PENDING))
                .forEach(System.out::println);

        sout("Status Equals 'APPROVED' And Detail IsNotNull");
        productRepository.findAll(ProductSpecs.statusEqualsAndDetailIsNotNull(Product.Status.APPROVED))
                .forEach(System.out::println);

        sout("Name like '1' And Status Equals 'APPROVED'");
        productRepository.findAll(
                Specification
                        .where(ProductSpecs.nameLike("1"))
                        .and(ProductSpecs.statusEquals(Product.Status.APPROVED))
        ).forEach(System.out::println);
    }

    private void cache() {
        try {
            productService.save(Product.builder().name("java").code("P001").score(88.88).build());
            productService.save(Product.builder().name("dart").code("P002").score(66.66).build());
            productService.save(Product.builder().name("php").code("P003").score(33.33).build());
            productService.save(Product.builder().name("python").code("P004").score(55.55).build());
            log.info("######################################################");

            productsCache();
            info(1);
            log.info(productService.findAll().size());

            info(2);
            log.info(productService.findAll().size());
            productsCache();
            log.info("######################################################");

            productCache();
            info(1L, 1);
            log.info(productService.find(1L));
            info(1L, 2);
            log.info(productService.find(1L));
            productCache();

            log.info("######################################################");
            info(3L, 1);
            log.info(productService.find(3L));
            productCache();
            info(3L, 2);
            log.info(productService.find(3L));

            log.info("######################################################");
            info(4L, 1);
            log.info(productService.find(4L));
            productCache();
            info(4L, 2);
            log.info(productService.find(4L));

            log.info("######################################################");

            Product product = Product.builder().id(1L).name("java").code("P001").score(99.99).build();
            productService.update(product);
            productCache();
            Product java = productService.findProductTemp(1L);
            log.info(java);
            productCache();

            log.info("######################################################");
            log.info("evict dart");
            productService.delete(2L);
            productCache();

            log.info("######################################################");
            log.info("evict all");
            productCacheConfig.evictAll();
            productCache();
            productsCache();


        } catch (Exception e) {
            log.error(e);
        }
    }

    private void productsCache() {
        log.info("Cache name PROCUCTS : {}",
                ((CaffeineCache) Objects.requireNonNull(simpleCacheManager.getCache(ProductCacheConfig.CacheName.PRODUCTS)))
                        .getNativeCache().asMap());
    }

    private void productCache() {
        log.info("Cache name PRODUCT : {}",
                ((CaffeineCache) Objects.requireNonNull(simpleCacheManager.getCache(ProductCacheConfig.CacheName.PRODUCT)))
                        .getNativeCache().asMap());
    }

    private void info(int round) {
        log.info("find all product round {}", round);
    }

    private void info(Long id, int round) {
        log.info("find product id : {} round {}", id, round);
    }

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    // 1000 ms = 1 s
    @Scheduled(fixedDelay = 2000)  // 2s
    public void scheduleFixedDelayTask() throws InterruptedException {
        // [1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1]
        // [      5s     ] task1
        //                      [      5s     ] task2
        //                                           [      5s     ] task3
        TimeUnit.SECONDS.sleep(5);
        log.info(Thread.currentThread().getName() + " : FIXED-DELAY Task :: {}", dateTimeFormatter.format(LocalTime.now()));
    }

    @Scheduled(fixedRate = 3000)  // 3s
    public void scheduleFixedRateTask() throws InterruptedException {
        // [1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1]
        // [      5s     ] task1
        //          [      5s     ] task2
        //                   [      5s     ] task3
        TimeUnit.SECONDS.sleep(5);
        log.info(Thread.currentThread().getName() + " : FIXED-RATE Task :: {}", dateTimeFormatter.format(LocalTime.now()));
    }

    // run every 3 sec
    // วินาที นาที ชั่วโมง วัน เดือน วันใดบ้าง
    @Scheduled(cron = "0/3 * * * * *")
    public void scheduleCronExpressionTask() throws InterruptedException {
        // [1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1][1]
        // [   3s  ] task1
        //          [   3s  ] task2
        //                   [   3s  ] task3
        TimeUnit.SECONDS.sleep(3);
        log.info(Thread.currentThread().getName() + " : CRON TASK :: {}", dateTimeFormatter.format(LocalTime.now()));
    }

    private void asynchronous() {
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            productService.save(Product.builder().name("apple").code("F001").build());
            productService.save(Product.builder().name("lemon").code("F002").build());
            productService.save(Product.builder().name("coconut").code("F003").build());

            CompletableFuture<Product> apple = productService.find("apple");
            CompletableFuture<Product> lemon = productService.find("lemon");
            CompletableFuture<Product> coconut = productService.find("coconut");

            List<CompletableFuture<Product>> completableFutures = Arrays.asList(apple, lemon, coconut);
            CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[completableFutures.size()]))
                    .whenComplete(((aVoid, throwable) ->
                            completableFutures.forEach(productCompletableFuture -> log.info(productCompletableFuture.join()))
                    )).join();
            stopWatch.stop();
            log.info("Elapsed Time : " + stopWatch.getTotalTimeSeconds() + " Sec");
            //productService.voidMethod();
        } catch (InterruptedException e) {
            log.error(e);
        }
    }

    private void retryAndRecover() {
        // Example 1 use Annotation @Retryable
        productService.save(Product.builder().name("lemon").build());
        log.info(productService.findAll());
        productService.delete(1L);
        log.info(productService.findAll());

        // Example 2 use RetryTemplate
        productService.templateRetry();
    }

    private void asyncProperties() {
        log.info("resource from property " + asyncProperty);
    }

    private void profile() {
        //log.info(testProfile.calculate(2, 5));
    }

    private void profile2() {
        log.info("Profile {}", customProperty);
    }

    private void beanAndQualifier() {
        log.info("Bean and Qualifier : {}", calculator.calculate(5, 4));
    }*/

}

