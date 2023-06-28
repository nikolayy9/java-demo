package com.example.demo.model;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record ProductResponse(
        String name,
        BigDecimal sellingPrice,
        LocalDate validUntil
)
{
}
