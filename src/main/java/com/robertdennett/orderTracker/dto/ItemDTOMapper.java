package com.robertdennett.orderTracker.dto;

import com.robertdennett.orderTracker.model.Item;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ItemDTOMapper implements Function<Item, ItemDTO> {

    public ItemDTO apply(Item i) {
        if (i == null) return null;
        return new ItemDTO(
                i.getId(),
                new ProductDTO(
                        i.getProduct().getId(),
                        i.getProduct().getName()
                ),
                i.getQuantity()
        );
    }
}
