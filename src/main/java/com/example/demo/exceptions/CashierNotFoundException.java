package com.example.demo.exceptions;

public class CashierNotFoundException extends RuntimeException
{
    public CashierNotFoundException(String message)
    {
        super(message);
    }
}
