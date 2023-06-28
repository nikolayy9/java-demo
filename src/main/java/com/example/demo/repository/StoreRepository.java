package com.example.demo.repository;

import com.example.demo.model.Store;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends MongoRepository<Store, String>
{
    Optional<Store> findById(String id);
}
