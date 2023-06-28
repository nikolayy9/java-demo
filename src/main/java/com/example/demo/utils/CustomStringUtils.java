package com.example.demo.utils;

import java.util.UUID;

public class CustomStringUtils
{

    public static boolean isValidString(String str)
    {
        return str != null && !str.isEmpty() && !str.isBlank();
    }

    public static String generateId()
    {
        return UUID.randomUUID()
                   .toString();
    }

}
