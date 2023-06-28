package com.example.demo.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Document
public record Checkout(
        @Id String id,
        String storeId,
        String name,
        Cashier cashier,
        LocalDate createdAt,
        LocalDateTime updatedAt
)
{
    public Checkout
    {
        if (name == null)
        {
            throw new IllegalArgumentException("Name is not valid");
        }
        if (storeId == null)
        {
            throw new IllegalArgumentException("Store id is not valid");
        }
    }
}
