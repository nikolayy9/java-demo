package com.example.demo.model;

import lombok.Builder;

import java.math.BigDecimal;

@Builder(toBuilder = true)
public record BillProduct(
        Product product,
        int quantity,
        BigDecimal totalSum
)
{
    public BillProduct
    {
        totalSum = product.sellingPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
