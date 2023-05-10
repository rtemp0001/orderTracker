package com.robertdennett.orderTracker.dto;

import com.robertdennett.orderTracker.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

    private final CartDTOMapper cartDTOMapper;

    public CustomerDTO apply(Customer customer) {
        if (customer == null) return null;
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getCarts()
                        .stream()
                        .map(cartDTOMapper)
                        .collect(Collectors.toList())
        );
    }
}
