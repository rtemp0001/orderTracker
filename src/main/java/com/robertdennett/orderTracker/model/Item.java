package com.robertdennett.orderTracker.model;

import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    @Id
    @SequenceGenerator(
            name = "cart_item_sequence",
            sequenceName = "cart_item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cart_item_sequence"
    )
    private Long id;

    @ManyToOne
    //@JsonBackReference
    private Cart cart;

    @ManyToOne
    //@JsonBackReference
    private Product product;

    private int quantity;
}
