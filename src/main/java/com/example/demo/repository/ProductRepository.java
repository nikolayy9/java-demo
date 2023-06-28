package com.example.demo.repository;

import com.example.demo.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>, CustomProductRepository
{
    Set<Product> findAllByValidUntilBetweenAndEligibleForSaleIsTrue(LocalDate from, LocalDate to);

    Set<Product> findAllByValidUntilAfter(LocalDate validUntilAfter);

    Optional<Product> findByStoreIdAndIdAndEligibleForSaleIsTrue(String productId, String id);

    List<Product> findAllByStoreId(String storeId);
}
