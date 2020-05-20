package ru.geekbrains.persist.repo;

import org.springframework.stereotype.Repository;
import ru.geekbrains.persist.entity.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductRepository {
    private AtomicLong identityGen;
    private Map<Long, Product> products;

    public ProductRepository() {
        identityGen = new AtomicLong(0);
        products = new ConcurrentHashMap<>();
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public Product findById(long id) {
        return products.get(id);
    }

    public void save(Product product) {
        long id = identityGen.incrementAndGet();
        product.setId(id);
        products.put(id, product);
    }
}
