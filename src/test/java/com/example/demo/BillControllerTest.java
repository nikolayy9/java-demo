package com.example.demo;

import com.example.demo.model.BillRequest;
import com.example.demo.model.BillResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

public class BillControllerTest extends BaseTest
{

    @Test
    public void billSanity()
    {
        var store = createStore();
        var restTemplate = restTemplateBuilder.build();
        var cashier = createCashier(store.id());
        var checkout = createCheckout(cashier.id(), store.id());

        var products = createProducts(store.id()).stream()
                                       .map(product ->
                                            {
                                                var quantity = (int) (Math.random() * 4) + 1;
                                                return Map.entry(product.id(), quantity);
                                            })
                                       .collect(Collectors.toMap(Map.Entry::getKey,
                                                                 Map.Entry::getValue));

        var billRequest = BillRequest.builder()
                                     .products(products)
                                     .customerCash(BigDecimal.valueOf(1000))
                                     .cashierId(cashier.id())
                                     .checkoutId(checkout.id())
                                     .build();

        var responseBody = restTemplate.exchange(BASE_URL + port + BILL_PATH,
                                                 HttpMethod.POST,
                                                 createHttpEntity(billRequest, store.id()),
                                                 BillResponse.class);

        Assertions.assertNotNull(responseBody.getBody());
        Assertions.assertNotNull(responseBody.getBody().id());
        Assertions.assertNotNull(responseBody.getBody().createdAt());
        Assertions.assertNotNull(responseBody.getBody().checkoutResponse());
        Assertions.assertNotNull(responseBody.getBody().billProductsResponse());
    }
}
