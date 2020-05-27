package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.persist.repo.ProductRepository;

import java.math.BigDecimal;

@RequestMapping("/product")
@Controller
public class ProductController {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(name = "minCost", required = false) BigDecimal minCost,
            @RequestParam(name = "maxCost", required = false) BigDecimal maxCost
    ) {
        if (minCost == null && maxCost == null) {
            model.addAttribute("products", productRepository.findAll());
        } else if (minCost != null && maxCost == null) {
            model.addAttribute("products", productRepository.findByCostGreaterThanEqual(minCost));
        } else if (minCost == null && maxCost != null) {
            model.addAttribute("products", productRepository.findByCostLessThanEqual(maxCost));
        } else {
            model.addAttribute("products", productRepository.findByCostBetween(minCost, maxCost));
        }
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productRepository.save(product);
        return "redirect:/product";
    }

}
