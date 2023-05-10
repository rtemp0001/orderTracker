package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.NewItemRequest;
import com.robertdennett.orderTracker.exception.BadRequestException;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.model.Item;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.repository.CartRepository;
import com.robertdennett.orderTracker.repository.ItemRepository;
import com.robertdennett.orderTracker.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    private ItemService underTest;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CartRepository cartRepository;

    @Mock
    private ProductRepository productRepository;


    @BeforeEach
    void setUp() {
        underTest = new ItemService(itemRepository, cartRepository, productRepository);
    }

    @Test
    void testAddItemToCart() {
        // confirm that it throws an exception if the cart isn't found
        // and that we don't call save()
        NewItemRequest request1 = new NewItemRequest(1L, 1L, 0);
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.addItemToCart(request1))
                .withMessage("No cart found with id 1");

        verify(itemRepository, never()).save(any());

        // set up cart
        Cart cart = Cart.builder()
                .id(1L)
                .cartDate(LocalDate.now())
                .build();

        given(cartRepository.findById(1L))
                .willReturn(Optional.of(cart));

        NewItemRequest request2 = new NewItemRequest(1L, 1L, 0);

        // confirm that it throws an exception if the product isn't found
        // and that we don't call save()
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.addItemToCart(request2))
                .withMessage("No product found with id 1");

        // set up product
        Product product = Product.builder()
                .id(1L)
                .name("foo")
                .build();

        given(productRepository.findById(1L))
                .willReturn(Optional.of(product));

        NewItemRequest request3 = new NewItemRequest(1L, 1L, 0);

        // confirm that it throws an exception if the quantity <= 0
        // and that we don't call save()
        assertThatExceptionOfType(BadRequestException.class)
                .isThrownBy(() -> underTest.addItemToCart(request3))
                .withMessage("Quantity must be greater than 0");

        //confirm item is created when all params are valid
        NewItemRequest request4 = new NewItemRequest(1L, 1L, 5);

        underTest.addItemToCart(request4);

        ArgumentCaptor<Item> itemArgumentCaptor =
                ArgumentCaptor.forClass(Item.class);

        verify(itemRepository).save(itemArgumentCaptor.capture());

        Item capturedItem = itemArgumentCaptor.getValue();
        assertThat(capturedItem.getCart()).isEqualTo(cart);
        assertThat(capturedItem.getProduct().getName()).isEqualTo("foo");
        assertThat(capturedItem.getQuantity()).isEqualTo(5);

    }

    @Test
    void testDeleteItemFromCart() {
        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.deleteItemFromCart(1L, 1L))
                .withMessage("Cart item does not exist with id 1");

        verify(itemRepository, never()).deleteById(anyLong());

        Item item = Item.builder()
                .id(1L)
                .build();

        given(itemRepository.existsById(1L))
                .willReturn(true);

        assertThatExceptionOfType(ResourceNotFoundException.class)
                .isThrownBy(() -> underTest.deleteItemFromCart(1L, 1L))
                .withMessage("No cart exists for cart id 1");

        verify(itemRepository, never()).deleteById(anyLong());

        Cart cart = Cart.builder()
                .id(1L)
                .items(Set.of(item))
                .build();

        given(cartRepository.findById(1L))
                .willReturn(Optional.of(cart));

        underTest.deleteItemFromCart(1L, 1L);

        verify(itemRepository).deleteById(1L);

    }
}