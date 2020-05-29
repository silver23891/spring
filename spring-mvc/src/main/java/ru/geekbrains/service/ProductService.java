package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Product> findByParams(BigDecimal minCost, BigDecimal maxCost) {
        if (minCost == null && maxCost == null) {
            return productRepository.findAll();
        } else if (minCost != null && maxCost == null) {
            return productRepository.findByCostGreaterThanEqual(minCost);
        } else if (minCost == null) {
            return productRepository.findByCostLessThanEqual(maxCost);
        }
        return productRepository.findByCostBetween(minCost, maxCost);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> findByCostGreaterThanEqual(BigDecimal minCost) {
        return productRepository.findByCostGreaterThanEqual(minCost);
    }

    public List<Product> findByCostLessThanEqual(BigDecimal maxCost) {
        return productRepository.findByCostLessThanEqual(maxCost);
    }

    public List<Product> findByCostBetween(BigDecimal minCost, BigDecimal maxCost) {
        return productRepository.findByCostBetween(minCost, maxCost);
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public void save(Product product) {
        productRepository.save(product);
    }
}
