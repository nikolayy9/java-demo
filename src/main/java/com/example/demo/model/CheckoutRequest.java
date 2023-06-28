package com.example.demo.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record CheckoutRequest(
        String name,
        String cashierId
)
{
}
