package com.example.demo.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record StoreRequest(
        String name,
        String address
)
{
}
