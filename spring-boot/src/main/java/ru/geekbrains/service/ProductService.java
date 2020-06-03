package ru.geekbrains.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;
import ru.geekbrains.persist.repo.ProductSpecification;

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

    public Page<Product> findByParams(BigDecimal minCost, BigDecimal maxCost, String title, Pageable pageable) {
        Specification<Product> specification = ProductSpecification.trueLiteral();
        if (minCost != null) {
            specification = specification.and(ProductSpecification.costGreaterThanEqual(minCost));
        }
        if (maxCost != null) {
            specification = specification.and(ProductSpecification.costLessThanEqual(maxCost));
        }
        if (title != null && !title.isEmpty()) {
            specification = specification.and(ProductSpecification.titleLike(title));
        }
        return productRepository.findAll(specification, pageable);
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

    public void delete(Long id) {
        productRepository.deleteById(id);
    }
}
