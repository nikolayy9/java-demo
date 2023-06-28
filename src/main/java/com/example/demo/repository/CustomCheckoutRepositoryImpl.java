package com.example.demo.repository;

import com.example.demo.model.Cashier;
import com.example.demo.model.Checkout;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;


@Repository
@RequiredArgsConstructor
public class CustomCheckoutRepositoryImpl implements CustomCheckoutRepository
{
    private final MongoTemplate mongoTemplate;

    @Override
    public Checkout update(String storeId, String name, Cashier cashier, String id)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("storeId").is(storeId));

        Update update = new Update();
        if (name != null)
        {
            update.set("name", name);
        }
        if (cashier != null)
        {
            update.set("cashier", cashier);
        }
        update.set("updatedAt", LocalDate.now());

        mongoTemplate.updateFirst(query, update, Checkout.class);

        return mongoTemplate.find(Query.query(Criteria.where("id").is(id)), Checkout.class)
                            .stream()
                            .findFirst()
                            .orElseThrow(() -> new RuntimeException("Checkout not found"));
    }
}
