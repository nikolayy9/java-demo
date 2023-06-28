package com.example.demo.repository;

import com.example.demo.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository
{
    private final MongoTemplate mongoTemplate;

    @Override
    public void updateSellingPrice(Product product)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(product.id()));
        query.addCriteria(Criteria.where("storeId").is(product.storeId()));

        Update update = new Update();
        update.set("sellingPrice", product.sellingPrice());

        mongoTemplate.updateFirst(query, update, Product.class);
    }

    @Override
    public void updateProductQuantity(String storeId, String id, long quantity)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        query.addCriteria(Criteria.where("storeId").is(storeId));

        Update update = new Update();
        update.set("quantity", quantity);

        mongoTemplate.updateFirst(query, update, Product.class);
    }

    @Override
    public void updateEligibleForSale(Product product)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(product.id()));

        Update update = new Update();
        update.set("eligibleForSale", product.eligibleForSale());

        mongoTemplate.updateFirst(query, update, Product.class);
    }
}
