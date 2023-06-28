package com.example.demo.exceptions;

public class InsufficientQuantityException extends RuntimeException
{
    public InsufficientQuantityException(String message)
    {
        super(message);
    }
}

