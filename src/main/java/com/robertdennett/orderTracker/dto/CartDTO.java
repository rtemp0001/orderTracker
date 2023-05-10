package com.robertdennett.orderTracker.dto;

import java.time.LocalDate;
import java.util.List;

public record CartDTO(Long id, List<ItemDTO> items, LocalDate date) {}
