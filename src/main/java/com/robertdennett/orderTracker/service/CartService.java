package com.robertdennett.orderTracker.service;

import com.robertdennett.orderTracker.dto.CartDTO;
import com.robertdennett.orderTracker.dto.CartDTOMapper;
import com.robertdennett.orderTracker.exception.ResourceNotFoundException;
import com.robertdennett.orderTracker.model.Customer;
import com.robertdennett.orderTracker.model.Cart;
import com.robertdennett.orderTracker.repository.CustomerRepository;
import com.robertdennett.orderTracker.repository.ItemRepository;
import com.robertdennett.orderTracker.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;

    private final CustomerRepository customerRepository;

    private final ItemRepository itemRepository;

    private final CartDTOMapper cartDTOMapper;

    public List<CartDTO> getCarts() {
        return cartRepository.findAll()
                .stream()
                .map(cartDTOMapper)
                .collect(Collectors.toList());
    }

//    public List<CartDTO> getCartsForCustomer(Long customerId) {
//        return cartRepository.findByCustomerId(customerId)
//                .stream()
//                .map(cartDTOMapper)
//                .collect(Collectors.toList());
//    }

    public void createNewCart(Long customerId) {
        Optional<Customer> cust = customerRepository.findById(customerId);
        if (!cust.isPresent()) {
            throw new ResourceNotFoundException("Unable to create cart.  Invalid customer id: " + customerId);
        }

        Cart cart = Cart.builder()
                .cartDate(LocalDate.now())
                .customer(cust.get())
                .build();
        cartRepository.save(cart);

    }

    public void deleteCart(long cartId) {
        if (!cartRepository.existsById(cartId)) {
            throw new ResourceNotFoundException("Cart doesn't exist for id " + cartId);
        }
        cartRepository.deleteById(cartId);
    }

//    public void deleteItemFromCart(long cartId, long itemId) {
//        Optional<Cart> cart = cartRepository.findById(cartId);
//        if (!cart.isPresent()) {
//            throw new IllegalArgumentException("No cart exists for cart id " + cartId);
//        }
//
//        Optional<Item> item = itemRepository.findById(itemId);
//        if (!item.isPresent()) {
//            throw new IllegalArgumentException("No item exists with item id " + itemId);
//        }
//        cart.get().getItems().remove(item.get());
//        //itemRepository.delete(item.get());
//    }
}
