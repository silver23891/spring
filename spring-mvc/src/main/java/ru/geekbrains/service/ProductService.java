package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findByParams(BigDecimal minCost, BigDecimal maxCost, Pageable pageable) {
        if (minCost == null && maxCost == null) {
            return productRepository.findAll(pageable);
        } else if (minCost != null && maxCost == null) {
            return productRepository.findByCostGreaterThanEqual(minCost, pageable);
        } else if (minCost == null) {
            return productRepository.findByCostLessThanEqual(maxCost, pageable);
        }
        return productRepository.findByCostBetween(minCost, maxCost, pageable);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
