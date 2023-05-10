package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.NewItemRequest;
import com.robertdennett.orderTracker.exception.BadRequestException;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.model.Item;
import com.robertdennett.orderTracker.model.Product;
import com.robertdennett.orderTracker.repository.ItemRepository;
import com.robertdennett.orderTracker.repository.CartRepository;
import com.robertdennett.orderTracker.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    private final CartRepository cartRepository;

    private final ProductRepository productRepository;

    public void addItemToCart(NewItemRequest request) {
        Optional<Cart> cart = cartRepository.findById(request.cartId());

        if (!cart.isPresent()) {
            throw new ResourceNotFoundException("No cart found with id " + request.cartId());
        }

        Optional<Product> product = productRepository.findById(request.productId());

        if (!product.isPresent()) {
            throw new ResourceNotFoundException("No product found with id " + request.productId());
        }

        if (request.quantity() < 1) {
            throw new BadRequestException("Quantity must be greater than 0");
        }

        Item item = Item.builder()
                .cart(cart.get())
                .product(product.get())
                .quantity(request.quantity())
                .build();
        itemRepository.save(item);
    }

    public void deleteItemFromCart(long itemId, long cartId) {
        if (!itemRepository.existsById(itemId)) {
            throw new ResourceNotFoundException("Cart item does not exist with id " + itemId);
        }
        Optional<Cart> cart = cartRepository.findById(cartId);
        if (!cart.isPresent()) {
            throw new ResourceNotFoundException("No cart exists for cart id " + cartId);
        }

        itemRepository.deleteById(itemId);

    }
}
