package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekbrains.persist.entity.Product;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Optional;

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
            @RequestParam(name = "maxCost", required = false) BigDecimal maxCost,
            @RequestParam(name = "page", required = false, defaultValue =  "1") Integer page,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Page productsPage = productService.findByParams(
                minCost,
                maxCost,
                PageRequest.of(Integer.valueOf(page)-1, Integer.valueOf(pageSize))
        );
        model.addAttribute("productsPage", productsPage);
        model.addAttribute("minCost", minCost);
        model.addAttribute("maxCost", maxCost);
        model.addAttribute(
                "previousPage",
                productsPage.hasPrevious() ? productsPage.previousPageable().getPageNumber() + 1 : -1
        );
        model.addAttribute(
                "nextPage",
                productsPage.hasNext() ? productsPage.nextPageable().getPageNumber() + 1 : -1
        );
        return "products";
    }

    @GetMapping("new")
    public String createProduct(Model model) {
        model.addAttribute("product", new Product());
        return "product";
    }

    @GetMapping("update")
    public String updateProduct(@RequestParam(name = "productId") Long productId, Model model) {
        Optional<Product> product = productService.findById(productId);
        model.addAttribute("product", product.get());
        return "product";
    }

    @PostMapping
    public String saveProduct(@Valid Product product, BindingResult bindingResult) {
        int compare = product.getCost().compareTo(new BigDecimal(0));
        if (compare < 1) {
            bindingResult.rejectValue("cost", "", "цена должна быть больше нуля");
            return "product";
        }
        if (bindingResult.hasErrors()) {
            return "product";
        }
        productService.save(product);
        return "redirect:/product";
    }

}
