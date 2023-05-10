package com.robertdennett.orderTracker.dto;

public record NewItemRequest(long cartId, long productId, int quantity) {}
