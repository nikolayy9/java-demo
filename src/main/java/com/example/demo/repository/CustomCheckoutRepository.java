package com.example.demo.repository;

import com.example.demo.model.Cashier;
import com.example.demo.model.Checkout;

public interface CustomCheckoutRepository
{
    Checkout update(String storeId, String name, Cashier cashier, String id);
}
