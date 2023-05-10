package com.robertdennett.orderTracker.config;

import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.repository.CustomerRepository;
import com.robertdennett.orderTracker.repository.CartRepository;
import com.robertdennett.orderTracker.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class OrderTrackerConfig {

    @Bean
    public CommandLineRunner commandLineRunner(
            CartRepository cartRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository) {
        return args -> {
            Customer cust = Customer.builder()
                    .name("foo")
                    .build();


            Cart cart1 = Cart.builder()
                    .cartDate(LocalDate.now())
                    .customer(cust)
                    .build();

            Cart cart2 = Cart.builder()
                    .cartDate(LocalDate.now())
                    .customer(cust)
                    .build();

            cust.setCarts(List.of(cart1, cart2));

            Product product1 = Product.builder()
                    .name("Rubber baby buggy bumpers")
                    .build();

            Product product2 = Product.builder()
                    .name("Liquid soap")
                    .build();

            customerRepository.save(cust);
            cartRepository.saveAll(List.of(cart1, cart2));
            productRepository.saveAll(List.of(product1, product2));
        };
    }
}
