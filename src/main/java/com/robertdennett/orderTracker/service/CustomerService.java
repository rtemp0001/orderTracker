package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.*;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerDTOMapper customerDTOMapper;

    private final CartDTOMapper cartDTOMapper;


    public List<CustomerDTO> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerDTOMapper)
                .toList();
    }

    public CustomerDTO getCustomer(long id) {
        Optional<Customer> cust =  customerRepository.findById(id);
        if (!cust.isPresent()) {
            throw new ResourceNotFoundException("No customer with id " + id);
        }
        return customerDTOMapper.apply(cust.get());
    }

    public List<CartDTO> getCartsForCustomer(long customerId) {
        Optional<Customer> cust = customerRepository.findById(customerId);
        if (!cust.isPresent()) {
            throw new ResourceNotFoundException("No customer with id " + customerId);
        }
        return cust.get().getCarts()
                .stream()
                .map(cartDTOMapper)
                .toList();
    }

    public void createCustomer(NewCustomerRequest request) {
        Customer cust = Customer.builder()
                .name(request.name())
                .build();
        customerRepository.save(cust);
    }

}
