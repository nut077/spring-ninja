package com.github.nut077.springninja.controller;

import com.github.nut077.springninja.dto.ProductDto;
import com.github.nut077.springninja.entity.Product;
import com.github.nut077.springninja.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Log4j2
@RequiredArgsConstructor
@RestController
public class ProductController extends CommonController {

    private final ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> products(@RequestParam(required = false) Product.Status status) {
        return ResponseEntity.ok(productService.findAll(status));
    }

    // get one product
    // /products/1              status 200 OK
    // /products/0              status 500 Internal Server Error
    // /products/abc            status 400 Bad Request
    // /products/abc            status 405 Method Not Allowed (regex)

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> product(@PathVariable Long id) {
        /*HttpHeaders headers = new HttpHeaders();
        headers.add("x-developer", "ball");
        headers.add("x-company", "java-verse");

        ResponseEntity.ok().body(productService.find(id));
        ResponseEntity.ok(productService.find(id));
        ResponseEntity.status(HttpStatus.OK).headers(headers).body(productService.find(id));
        new ResponseEntity(productService.find(id), HttpStatus.OK);
        new ResponseEntity(productService.find(id),headers, HttpStatus.OK);*/

        return ResponseEntity.ok(productService.find(id));
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> save(@RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.save(dto));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> replace(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return ResponseEntity.ok(productService.replace(id, productDto));
    }

    @PatchMapping("/products/{id}/score/{score}")
    public ResponseEntity<Integer> updateScore(@PathVariable Long id, @PathVariable double score) {
        return ResponseEntity.ok(productService.updatedScore(id, score));
    }

    @DeleteMapping("/products/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    //Exact vs Encoded value
    // /products/vs/abc+123?encode=abc+123
    @GetMapping("/products/vs/{exact}")
    public void vs(@PathVariable String exact, @RequestParam String encode) {
        log.info(() -> "value from path-variable : " + exact);
        log.info(() -> "value from request-param : " + encode);
    }

    // products/date/2019-1-15?dMy=16-1-2019
    // ใช้ใน configuration class LocalDateConverter
    @GetMapping("/products/date")
    public void date(
            @RequestParam
            //@DateTimeFormat(pattern = "d-M-yyyy")
                    LocalDate dMy
    ) {
        log.info(() -> "date from request-param : " + dMy);

    }

}
