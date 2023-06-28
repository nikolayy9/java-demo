package com.example.demo.repository;

import com.example.demo.model.Checkout;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckoutRepository extends MongoRepository<Checkout, String>, CustomCheckoutRepository
{
    Optional<Checkout> findByStoreIdAndId(String storeId, String s);

    List<Checkout> findAllByStoreId(String storeId);
}
