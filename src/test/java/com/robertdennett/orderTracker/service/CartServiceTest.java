package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.CartDTOMapper;
import com.robertdennett.orderTracker.dto.ItemDTOMapper;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.model.Item;
import com.robertdennett.orderTracker.repository.CartRepository;
import com.robertdennett.orderTracker.repository.CustomerRepository;
import com.robertdennett.orderTracker.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CartServiceTest {

    private CartService underTest;

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CartRepository cartRepository;
    @Mock
    private ItemRepository itemRepository;

    private CartDTOMapper cartDTOMapper;


    @BeforeEach
    void setUp() {
        cartDTOMapper = new CartDTOMapper(new ItemDTOMapper());
        underTest = new CartService(
                cartRepository,
                customerRepository,
                itemRepository,
                cartDTOMapper
        );
    }

    @Test
    void testGetCarts() {
        underTest.getCarts();

        // just make sure it calls findAll
        verify(cartRepository).findAll();
    }

    @Test
    void testCreateNewCart() {
        // confirm that it throws an exception if the resource isn't found
        // and that we don't call save()
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.createNewCart(1L));

        verify(cartRepository, never()).save(any());

        // set up objects
        Customer cust = Customer.builder()
                .id(1L)
                .name("foo")
                .build();

        given(customerRepository.findById(1L))
                .willReturn(Optional.of(cust));

        // confirm that cart is created
        underTest.createNewCart(1L);

        ArgumentCaptor<Cart> cartArgumentCaptor =
                ArgumentCaptor.forClass(Cart.class);

        verify(cartRepository).save(cartArgumentCaptor.capture());

        Cart capturedCart = cartArgumentCaptor.getValue();
        assertThat(capturedCart.getCustomer()).isEqualTo(cust);
        assertThat(capturedCart.getItems()).isNull();

    }

    @Test
    void deleteCart() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.deleteCart(1L));

        verify(cartRepository, never()).deleteById(anyLong());

        Cart cart = Cart.builder()
                .id(1L)
                .cartDate(LocalDate.now())
                .items(new HashSet<Item>())
                .build();

        given(cartRepository.existsById(1L))
                .willReturn(true);

        underTest.deleteCart(1L);

        verify(cartRepository).deleteById(1L);

    }
}