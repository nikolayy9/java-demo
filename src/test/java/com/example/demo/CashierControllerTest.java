package com.example.demo;

import com.example.demo.model.Cashier;
import com.example.demo.model.CashierRequest;
import com.example.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class CashierControllerTest extends BaseTest
{

    @Test
    public void cashiersSanity()
    {
        var store = createStore();

        var requests = List.of(
                CashierRequest.builder()
                              .name("John Doe")
                              .salary(100)
                              .build(),

                CashierRequest.builder()
                              .name("Mick Jagger")
                              .salary(200)
                              .build()
                              );

        var restTemplate = restTemplateBuilder.build();

        ResponseEntity<String> createResponse = restTemplate.exchange(BASE_URL + port + CASHIER_PATH,
                                                                      HttpMethod.POST,
                                                                      createHttpEntity(requests, store.id()),
                                                                      String.class);

        assertCashiers(createResponse);

        ResponseEntity<String> searchResponse = restTemplate.exchange(BASE_URL + port + CASHIER_PATH,
                                                                      HttpMethod.GET,
                                                                      createHttpEntity(null, store.id()),
                                                                      String.class);

        assertCashiers(searchResponse);
    }

    private void assertCashiers(ResponseEntity<String> response)
    {
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        var cashiers = JsonUtils.fromJson(response.getBody(), new TypeReference<List<Cashier>>()
        {
        });
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        Assertions.assertEquals(2, cashiers.size());

        Assertions.assertEquals("John Doe", cashiers.get(0).name());
        Assertions.assertEquals(100, cashiers.get(0).salary());

        Assertions.assertEquals("Mick Jagger", cashiers.get(1).name());
        Assertions.assertEquals(200, cashiers.get(1).salary());
    }
}