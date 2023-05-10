package com.robertdennett.orderTracker.dto;

import java.util.List;

public record CustomerDTO(Long id, String name, List<CartDTO> carts) {}
