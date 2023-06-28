package com.example.demo.model;

import com.example.demo.utils.CustomStringUtils;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder(toBuilder = true)
@Document
public record Store(
        @Id String id,
        String name,
        String address
)
{

    public Store
    {
        if (!CustomStringUtils.isValidString(address))
        {
            throw new IllegalArgumentException("Address is not valid");
        }
        if (!CustomStringUtils.isValidString(name))
        {
            throw new IllegalArgumentException("Name is not valid");
        }
    }
}
