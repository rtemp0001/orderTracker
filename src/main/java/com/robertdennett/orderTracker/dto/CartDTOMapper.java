package com.robertdennett.orderTracker.dto;

import com.robertdennett.orderTracker.model.Cart;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartDTOMapper implements Function<Cart, CartDTO> {

    private final ItemDTOMapper itemsDTOMapper;

    public CartDTO apply(Cart c) {
        if (c == null) return null;
        return new CartDTO(
                c.getId(),
                c.getItems()
                        .stream()
                        .map(itemsDTOMapper)
                        .collect(Collectors.toList()),
                c.getCartDate()
        );
    }
}
