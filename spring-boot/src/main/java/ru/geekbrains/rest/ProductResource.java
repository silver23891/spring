package ru.geekbrains.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
public class ProductResource {
    ProductService productService;

    @Autowired
    public ProductResource(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public List<Product> getAll() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable long id) {
        return productService.findById(id).orElseThrow(HttpNotFoundException::new);
    }

    @PostMapping
    public void createProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @PutMapping
    public void updateProduct(@RequestBody Product product) {
        productService.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable long id) {
        productService.delete(id);
    }

    @ExceptionHandler
    public ResponseEntity<HttpNotFoundResponse> httpNotFoundExceptionResponseHandler(HttpNotFoundException e) {
        HttpNotFoundResponse httpNotFoundResponse = new HttpNotFoundResponse();
        httpNotFoundResponse.setStatus(HttpStatus.NOT_FOUND.value());
        httpNotFoundResponse.setMessage("Not found");
        httpNotFoundResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(httpNotFoundResponse, HttpStatus.NOT_FOUND);
    }
}
