package com.robertdennett.orderTracker.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cart {

    @Id
    @SequenceGenerator(
            name = "cart_sequence",
            sequenceName = "cart_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "cart_sequence"
    )
    public Long id;

    @ManyToOne
    //@JsonBackReference
    private Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
    //@JsonManagedReference
    private Set<Item> items;

    private LocalDate cartDate;
}
