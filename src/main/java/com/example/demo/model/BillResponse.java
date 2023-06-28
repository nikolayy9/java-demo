package com.example.demo.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BillResponse(
        String id,
        List<BillProductResponse> billProductsResponse,
        CheckoutResponse checkoutResponse,
        LocalDateTime createdAt,
        BigDecimal totalSum
)
{
}
