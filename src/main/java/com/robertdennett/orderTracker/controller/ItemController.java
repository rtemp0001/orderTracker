package com.robertdennett.orderTracker.controller;

import com.robertdennett.orderTracker.dto.NewItemRequest;
import com.robertdennett.orderTracker.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public void addItemToCart(@RequestBody NewItemRequest request) {
        System.out.println("cart id " + request.cartId());
        System.out.println("product id " + request.productId());
        itemService.addItemToCart(request);
    }

    @DeleteMapping("{itemId}/cart/{cartId}")
    public void deleteItemFromCart(@PathVariable long itemId, @PathVariable long cartId) {
        itemService.deleteItemFromCart(itemId, cartId);
    }
}
