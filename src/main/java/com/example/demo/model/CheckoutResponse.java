package com.example.demo.model;

import lombok.Builder;

@Builder
public record CheckoutResponse(
        String name,
        CashierResponse cashier
)
{
}
