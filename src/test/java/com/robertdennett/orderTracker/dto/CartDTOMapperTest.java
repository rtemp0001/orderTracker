package com.robertdennett.orderTracker.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;

class CartDTOMapperTest {

    private CartDTOMapper underTest;

    @BeforeEach
    void setUp() {
        underTest = new CartDTOMapper(new ItemDTOMapper());
    }

    @Test
    void testApplyWithNull() {
        assertThatNoException().isThrownBy(() -> underTest.apply(null));
    }
}