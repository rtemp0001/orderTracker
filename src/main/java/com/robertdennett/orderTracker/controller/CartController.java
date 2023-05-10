package com.robertdennett.orderTracker.controller;

import com.robertdennett.orderTracker.dto.CartDTO;
import com.robertdennett.orderTracker.service.CustomerService;
import com.robertdennett.orderTracker.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private final CustomerService customerService;

    @GetMapping
    public List<CartDTO> getCarts() {
        return cartService.getCarts();
    }


    @PostMapping
    public void createNewCart(@RequestParam long customerId) {
        cartService.createNewCart(customerId);
    }

//    public void updateCart(Long cartId) {}

    @DeleteMapping("{cartId}")
    public void deleteCart(@PathVariable long cartId) {
        // TODO: only Customer owning cart should be allowed to do this
        cartService.deleteCart(cartId);
    }

//    @DeleteMapping("{cartId}/item/{itemId}")
//    public void deleteItemFromCart(@PathVariable long cartId, @PathVariable long itemId) {
//        cartService.deleteItemFromCart(cartId, itemId);
//    }

//    public void getProductsInCart(Long cartId) {}
}
