package ru.geekbrains.persist.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCostGreaterThanEqual(BigDecimal minCost);
    List<Product> findByCostLessThanEqual(BigDecimal maxCost);
    List<Product> findByCostBetween(BigDecimal minCost, BigDecimal maxCost);
}
