package ru.geekbrains.persist.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.geekbrains.persist.entity.Product;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p from Product p where p.title like %?1%")
    Page<Product> findAll(String title, Pageable pageable);
    @Query("select p from Product p where p.cost >= ?1 and p.title like %?2%")
    Page<Product> findByCostGreaterThanEqual(BigDecimal minCost, String title, Pageable pageable);
    @Query("select p from Product p where p.cost <= ?1 and p.title like %?2%")
    Page<Product> findByCostLessThanEqual(BigDecimal maxCost, String title, Pageable pageable);
    @Query("select p from Product p where p.cost between ?1 and ?2 and p.title like %?3%")
    Page<Product> findByCostBetween(BigDecimal minCost, BigDecimal maxCost, String title, Pageable pageable);
}
