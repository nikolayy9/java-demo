package com.example.demo.model;

import com.example.demo.utils.CustomStringUtils;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder(toBuilder = true)
@Document
public record Product(
        @Id String id,
        String storeId,
        String name,
        BigDecimal incomingPrice,
        BigDecimal sellingPrice,
        StockCategory category,
        LocalDate validUntil,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        boolean eligibleForSale,
        long quantity
)
{
    public Product
    {
        if (!CustomStringUtils.isValidString(storeId))
        {
            throw new IllegalArgumentException("Store id is not valid");
        }

        sellingPrice = sellingPrice.setScale(2, RoundingMode.HALF_UP);
    }
}
