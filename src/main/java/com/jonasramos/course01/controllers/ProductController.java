package com.jonasramos.course01.controllers;

import com.jonasramos.course01.domain.product.Product;
import com.jonasramos.course01.domain.product.ProductRepository;
import com.jonasramos.course01.domain.product.RequestProduct;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductRepository repository;
    @GetMapping
    public ResponseEntity getAll() {
        var allProducts = repository.findAllByActiveTrue();
        return ResponseEntity.ok(allProducts);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid RequestProduct data) {
        Product newProduct = new Product(data);
        repository.save(newProduct);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity update(@PathVariable String id, @RequestBody @Valid RequestProduct data) {
        Optional<Product> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product product = optionalProduct.get();
        product.setName(data.name());
        product.setPrice(data.price());

        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete (@PathVariable String id) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new EntityNotFoundException();
        }

        Product product = optionalProduct.get();
        product.setActive(false);

        return ResponseEntity.noContent().build();
    }
}
