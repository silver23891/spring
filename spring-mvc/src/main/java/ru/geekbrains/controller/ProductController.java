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
import ru.geekbrains.service.ProductService;

import java.math.BigDecimal;

@RequestMapping("/product")
@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public void setProductRepository(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String listProducts(
            Model model,
            @RequestParam(name = "minCost", required = false) BigDecimal minCost,
            @RequestParam(name = "maxCost", required = false) BigDecimal maxCost
    ) {
        model.addAttribute("products", productService.findByParams(minCost, maxCost));
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @PostMapping
    public String saveProduct(Product product) {
        productService.save(product);
        return "redirect:/product";
    }

}
