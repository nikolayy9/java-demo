package com.example.demo;

import com.example.demo.model.Cashier;
import com.example.demo.model.CashierRequest;
import com.example.demo.model.Checkout;
import com.example.demo.model.CheckoutRequest;
import com.example.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CheckoutControllerTest extends BaseTest
{
    @Test
    public void checkoutsSanity()
    {
        var store = createStore();
        var cashierRequest = CashierRequest.builder()
                                           .name("Mick Jagger")
                                           .salary(200)
                                           .build();

        var cashierResponse = restTemplateBuilder.build()
                                                 .exchange(BASE_URL + port + CASHIER_PATH,
                                                           HttpMethod.POST,
                                                           createHttpEntity(List.of(cashierRequest), store.id()),
                                                           String.class);

        var cashier = JsonUtils.fromJson(cashierResponse.getBody(), new TypeReference<List<Cashier>>()
        {
        });

        var requests = List.of(
                CheckoutRequest.builder()
                               .name("Checkout 1")
                               .cashierId("")
                               .build(),

                CheckoutRequest.builder()
                               .name("Checkout 2")
                               .cashierId(cashier.get(0).id())
                               .build());

        var restTemplate = restTemplateBuilder.build();

        var createResponse = restTemplate.exchange(BASE_URL + port + CHECKOUT_PATH,
                                                   HttpMethod.POST,
                                                   createHttpEntity(requests, store.id()),
                                                   String.class);

        assertCheckouts(createResponse);

        var searchResponse = restTemplate.exchange(BASE_URL + port + CHECKOUT_PATH,
                                                   HttpMethod.GET,
                                                   createHttpEntity(null, store.id()),
                                                   String.class);

        assertCheckouts(searchResponse);
    }

    private void assertCheckouts(ResponseEntity<String> response)
    {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        var checkouts = JsonUtils.fromJson(response.getBody(), new TypeReference<List<Checkout>>()
        {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(2, checkouts.size());

        Assertions.assertEquals("Checkout 1", checkouts.get(0).name());
        Assertions.assertNull(checkouts.get(0).cashier());

        Assertions.assertEquals("Checkout 2", checkouts.get(1).name());
        Assertions.assertNotNull(checkouts.get(1).cashier());
    }
}