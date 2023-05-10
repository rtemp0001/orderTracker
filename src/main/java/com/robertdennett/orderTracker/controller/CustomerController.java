package com.robertdennett.orderTracker.controller;

import com.robertdennett.orderTracker.dto.CustomerDTO;
import com.robertdennett.orderTracker.dto.NewCustomerRequest;
import com.robertdennett.orderTracker.dto.CartDTO;
import com.robertdennett.orderTracker.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("{customerId}")
    public CustomerDTO getCustomer(@PathVariable("customerId") long customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("{customerId}/carts")
    public List<CartDTO> getCartsForCustomer(@PathVariable("customerId") long customerId) {
        return customerService.getCartsForCustomer(customerId);
    }

    @PostMapping
    public void addNewCustomer(@RequestBody NewCustomerRequest request) {
        customerService.createCustomer(request);
    }

}

