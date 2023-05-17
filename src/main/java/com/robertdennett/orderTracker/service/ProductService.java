package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.NewProductRequest;
import com.robertdennett.orderTracker.dto.ProductDTO;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> new ProductDTO(p.getId(), p.getName()))
                .toList();
    }

    public void addProduct(NewProductRequest request) {
        Product product = Product.builder()
                .name(request.name())
                .build();
        productRepository.save(product);
    }
}
