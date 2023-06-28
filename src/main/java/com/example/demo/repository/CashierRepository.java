package com.example.demo.repository;

import com.example.demo.model.Cashier;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CashierRepository extends MongoRepository<Cashier, String>
{
    Optional<Cashier> findByStoreIdAndId(String storeId, String id);

    List<Cashier> findAllByStoreId(String storeId);
}
