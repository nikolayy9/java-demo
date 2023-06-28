package com.example.demo.model;

import com.example.demo.utils.CustomStringUtils;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder(toBuilder = true)
public record ProductRequest(
        String name,
        BigDecimal incomingPrice,
        StockCategory category,
        LocalDate validUntil,
        int quantity
)
{
    public ProductRequest
    {
        if (!CustomStringUtils.isValidString(name))
        {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        if (incomingPrice == null || incomingPrice.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new IllegalArgumentException("Incoming price must be greater than zero");
        }

        if (category == null)
        {
            throw new IllegalArgumentException("Category must not be null");
        }
        if (validUntil == null || validUntil.isBefore(LocalDate.now()))
        {
            throw new IllegalArgumentException("Valid until date must be in the future");
        }
        if (quantity <= 0)
        {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
    }
}
