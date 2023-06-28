package com.example.demo.repository;

import com.example.demo.model.Bill;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends MongoRepository<Bill, String>
{
    List<Bill> findAllByStoreId(String storeId);
}
