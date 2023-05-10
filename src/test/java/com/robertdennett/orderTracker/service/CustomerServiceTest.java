package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.*;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.model.Item;
import com.robertdennett.orderTracker.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private CustomerService underTest;

    @Mock
    private CustomerRepository customerRepository;


    private CustomerDTOMapper customerDTOMapper;
    private CartDTOMapper cartDTOMapper;

    @BeforeEach
    void setUp() {
        cartDTOMapper = new CartDTOMapper(new ItemDTOMapper());
        customerDTOMapper = new CustomerDTOMapper(cartDTOMapper);
        underTest = new CustomerService(
                customerRepository, customerDTOMapper, cartDTOMapper);
    }


    @Test
    void testGetCustomers() {
        underTest.getCustomers();

        // just make sure it calls findAll
        verify(customerRepository).findAll();
    }


    @Test
    void testGetCustomer() {

        // confirm that it throws an exception if the resource isn't found
        // and that we don't call save()
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.getCustomer(1L));

        verify(customerRepository, never()).save(any());

        // check to see that it works when it is found
        Customer c = Customer.builder()
                .id(1L)
                .name("foo")
                .carts(new ArrayList<Cart>())
                .build();

        given(customerRepository.findById(1L))
                .willReturn(Optional.of(c));

        assertThat(underTest.getCustomer(1L)).isEqualTo(customerDTOMapper.apply(c));
    }


    @Test
    void testGetCartsForCustomer() {
        // confirm that it throws an exception if the resource isn't found
        // and that we don't call save()
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.getCartsForCustomer(1L));

        verify(customerRepository, never()).save(any());

        // set up objects
        Cart cart = Cart.builder()
                .id(1L)
                .cartDate(LocalDate.now())
                .items(new HashSet<Item>())
                .build();
        Customer cust = Customer.builder()
                .id(1L)
                .name("foo")
                .carts(List.of(cart))
                .build();

        // confirm service returns list of CartDTOs
        given(customerRepository.findById(1L))
                .willReturn(Optional.of(cust));

        assertThat(underTest.getCartsForCustomer(1L))
                .contains(cartDTOMapper.apply(cart));

    }

    @Test
    void testCreateCustomer() {
        NewCustomerRequest request = new NewCustomerRequest("foo");
        underTest.createCustomer(request);

        ArgumentCaptor<Customer> customerArgumentCaptor =
                ArgumentCaptor.forClass(Customer.class);

        verify(customerRepository).save(customerArgumentCaptor.capture());

        Customer capturedCustomer = customerArgumentCaptor.getValue();
        assertThat(capturedCustomer.getName()).isEqualTo(request.name());
        assertThat(capturedCustomer.getCarts()).isNull();
    }
}