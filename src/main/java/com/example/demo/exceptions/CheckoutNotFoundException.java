package com.example.demo.exceptions;

public class CheckoutNotFoundException
        extends RuntimeException
{
    public CheckoutNotFoundException(String message)
    {
        super(message);
    }
}
