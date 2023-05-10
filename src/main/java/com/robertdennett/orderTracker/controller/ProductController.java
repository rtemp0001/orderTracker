package com.robertdennett.orderTracker.controller;

import com.robertdennett.orderTracker.dto.NewProductRequest;
import com.robertdennett.orderTracker.dto.ProductDTO;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping
    public void addProduct(@RequestBody NewProductRequest request) {
        productService.addProduct(request);
    }
}
