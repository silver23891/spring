package ru.geekbrains.persist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByCostGreaterThanEqual(BigDecimal minCost, Pageable pageable);
    Page<Product> findByCostLessThanEqual(BigDecimal maxCost, Pageable pageable);
    Page<Product> findByCostBetween(BigDecimal minCost, BigDecimal maxCost, Pageable pageable);
    Page<Product> findByTitleContaining(String title, Pageable pageable);
}
