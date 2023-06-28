package com.example.demo.repository;

import com.example.demo.model.Product;

public interface CustomProductRepository
{
    void updateSellingPrice(Product product);

    void updateProductQuantity(String storeId, String id, long quantity);

    void updateEligibleForSale(Product product);
}
