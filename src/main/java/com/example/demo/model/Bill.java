package com.example.demo.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

// Касовата бележка трябва да съдържат минимум следната информация: пореден номер,
//касиер, който издава касовата бележка, дата и час на издаване на касовата бележка, списък със
//стоки, които се включват в касовата бележка включително цената и количеството им и общата
//стойност, която трябва да се заплати от клиента.
@Builder(toBuilder = true)
@Document
public record Bill(
        @Id String id,
        String storeId,
        Cashier cashier,
        Checkout checkout,
        BigDecimal totalSum,
        LocalDateTime createdAt,
        List<BillProduct> products
)
{
}
