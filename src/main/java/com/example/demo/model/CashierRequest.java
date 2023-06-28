package com.example.demo.model;

import com.example.demo.utils.CustomStringUtils;
import lombok.Builder;

@Builder(toBuilder = true)
public record CashierRequest(
        String name,
        double salary
)
{

    public CashierRequest
    {
        if (!CustomStringUtils.isValidString(name))
        {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        if (salary <= 0)
        {
            throw new IllegalArgumentException("Salary must be greater than zero");
        }

    }
}