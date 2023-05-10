package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.NewProductRequest;
import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService underTest;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository);
    }

    @Test
    void testGetAllProducts() {
        underTest.getAllProducts();

        // just make sure it calls findAll
        verify(productRepository).findAll();
    }

    @Test
    void testAddProduct() {

        NewProductRequest request = new NewProductRequest("foo");
        underTest.addProduct(request);

        ArgumentCaptor<Product> productArgumentCaptor =
                ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productArgumentCaptor.capture());

        Product capturedProduct = productArgumentCaptor.getValue();
        assertThat(capturedProduct.getName()).isEqualTo(request.name());
    }
}