package com.robertdennett.orderTracker.dto;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class ItemDTOMapperTest {

    ItemDTOMapper underTest = new ItemDTOMapper();

    @Test
    void testApplyWithNull() {
        assertThatNoException().isThrownBy(() -> underTest.apply(null));
    }
}