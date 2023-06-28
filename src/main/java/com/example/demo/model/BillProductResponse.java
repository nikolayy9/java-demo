package com.example.demo.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BillProductResponse(
        ProductResponse productResponse,
        int quantity,
        BigDecimal totalSum
)
{
}
