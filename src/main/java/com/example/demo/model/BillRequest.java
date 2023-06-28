package com.example.demo.model;

import com.example.demo.utils.CustomStringUtils;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.Map;

@Builder(toBuilder = true)
public record BillRequest(
        String cashierId,
        String checkoutId,
        Map<String, Integer> products,
        BigDecimal customerCash
)
{
    public BillRequest
    {
        if (!CustomStringUtils.isValidString(cashierId))
        {
            throw new IllegalArgumentException("CashierId must not be null or empty");
        }
        if (!CustomStringUtils.isValidString(checkoutId))
        {
            throw new IllegalArgumentException("CheckoutId must not be null or empty");
        }
        if (products == null || products.isEmpty())
        {
            throw new IllegalArgumentException("Products must not be null or empty");
        }
        if (customerCash == null || customerCash.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new IllegalArgumentException("Customer cash must be greater than zero");
        }
    }
}
