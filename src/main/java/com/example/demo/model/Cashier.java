package com.example.demo.model;


import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

//В магазина работят касиери, които имат име, идентификационен номер и определена месечна
//заплата. На всяка от касите в магазина може да работи по един касиер. Всеки касиер може да
//работи на различна каса. На всяка от касите в магазина, касиерите маркират стоките, които
//клиентите искат да си купят. Ако клиентите имат достатъчно пари, за да си купят стоките,
//касиерите им ги продават и издават касови бележки.
@Builder
@Document
public record Cashier(
        @Id String id,
        String storeId,
        String name,
        double salary,
        LocalDate createdAt,
        LocalDate updatedAt
)
{
    public Cashier {
        if (name == null)
        {
            throw new IllegalArgumentException("Name is not valid");
        }
        if (storeId == null)
        {
            throw new IllegalArgumentException("Store id is not valid");
        }
        if (salary < 0)
        {
            throw new IllegalArgumentException("Salary is not valid");
        }
    }
}
