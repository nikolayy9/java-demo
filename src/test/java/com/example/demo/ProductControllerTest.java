package com.example.demo;

import com.example.demo.model.Product;
import com.example.demo.model.ProductRequest;
import com.example.demo.model.StockCategory;
import com.example.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ProductControllerTest extends BaseTest
{

    @Test
    public void productsSanity()
    {
        var store = createStore();

        var requests = List.of(
                ProductRequest.builder()
                              .quantity(10)
                              .name("Product 1")
                              .incomingPrice(BigDecimal.valueOf(100))
                              .category(StockCategory.FOOD)
                              .validUntil(LocalDate.now().plus(20, java.time.temporal.ChronoUnit.DAYS))
                              .build(),

                ProductRequest.builder()
                              .quantity(15)
                              .name("Product 2")
                              .category(StockCategory.NON_FOOD)
                              .incomingPrice(BigDecimal.valueOf(200))
                              .validUntil(LocalDate.now().plus(30, java.time.temporal.ChronoUnit.DAYS))
                              .build()
                              );

        var restTemplate = restTemplateBuilder.build();

        var loadResponse = restTemplate.exchange(BASE_URL + port + PRODUCTS_PATH,
                                                 HttpMethod.POST,
                                                 createHttpEntity(requests, store.id()),
                                                 String.class);

        assertProducts(loadResponse);

        var searchResponse = restTemplate.exchange(BASE_URL + port + PRODUCTS_PATH,
                                                   HttpMethod.GET,
                                                   createHttpEntity(null, store.id()),
                                                   String.class);

        assertProducts(searchResponse);
    }

    private void assertProducts(ResponseEntity<String> response)
    {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        var products = JsonUtils.fromJson(response.getBody(), new TypeReference<List<Product>>()
        {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(2, products.size());

        Assertions.assertEquals("Product 1", products.get(0).name());
        Assertions.assertEquals(BigDecimal.valueOf(100), products.get(0).incomingPrice());

        Assertions.assertEquals("Product 2", products.get(1).name());
        Assertions.assertEquals(BigDecimal.valueOf(200), products.get(1).incomingPrice());
    }
}
