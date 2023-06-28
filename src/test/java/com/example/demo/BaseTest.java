package com.example.demo;

import com.example.demo.model.Cashier;
import com.example.demo.model.CashierRequest;
import com.example.demo.model.Checkout;
import com.example.demo.model.CheckoutRequest;
import com.example.demo.model.Product;
import com.example.demo.model.ProductRequest;
import com.example.demo.model.StockCategory;
import com.example.demo.model.Store;
import com.example.demo.model.StoreRequest;
import com.example.demo.utils.CustomHeaders;
import com.example.demo.utils.JsonUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest
{
    protected static final String BILL_PATH = "/api/v1/bills";
    protected static final String STORES_PATH = "/api/v1/stores";
    protected static final String CASHIER_PATH = "/api/v1/cashiers";
    protected static final String PRODUCTS_PATH = "/api/v1/products";
    protected static final String CHECKOUT_PATH = "/api/v1/checkouts";
    protected static final String BASE_URL = "http://localhost:";

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    protected RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    protected int port;

    @AfterEach
    public void afterEach()
    {
        mongoTemplate.getDb().drop();
    }

    protected <T> HttpEntity<T> createHttpEntity(T body, String storeId)
    {
        HttpHeaders headers = new HttpHeaders();
        headers.set(CustomHeaders.STORE_ID, storeId);

        return new HttpEntity<>(body, headers);
    }

    protected Store createStore()
    {
        var storeRequest = StoreRequest.builder()
                                       .name("Test Store")
                                       .address("Test Address")
                                       .build();

        return restTemplateBuilder.build()
                                  .postForEntity(BASE_URL + port + STORES_PATH,
                                                 storeRequest,
                                                 Store.class)
                                  .getBody();
    }

    protected Cashier createCashier(String storeId)
    {
        var cashierRequest = List.of(CashierRequest.builder()
                                                   .name("John Doe")
                                                   .salary(100)
                                                   .build());

        var cashiersResponse = restTemplateBuilder.build()
                                                  .exchange(BASE_URL + port + CASHIER_PATH,
                                                            HttpMethod.POST,
                                                            createHttpEntity(cashierRequest, storeId),
                                                            String.class);

        return JsonUtils.fromJson(cashiersResponse.getBody(), new TypeReference<List<Cashier>>()
                        {
                        }).stream()
                        .findFirst()
                        .get();
    }

    protected Checkout createCheckout(String cashierId, String storeId)
    {
        var checkoutRequest = List.of(
                CheckoutRequest.builder()
                               .name("Checkout 1")
                               .cashierId(cashierId)
                               .build());

        var cashiersResponse = restTemplateBuilder.build()
                                                  .exchange(BASE_URL + port + CHECKOUT_PATH,
                                                            HttpMethod.POST,
                                                            createHttpEntity(checkoutRequest, storeId),
                                                            String.class);

        return JsonUtils.fromJson(cashiersResponse.getBody(), new TypeReference<List<Checkout>>()
                        {
                        }).stream()
                        .findFirst()
                        .get();
    }

    protected List<Product> createProducts(String storeId)
    {
        var requests = List.of(
                ProductRequest.builder()
                              .quantity(25)
                              .name("Chocolate")
                              .incomingPrice(BigDecimal.valueOf(2))
                              .category(StockCategory.FOOD)
                              .validUntil(LocalDate.now().plus(20, ChronoUnit.WEEKS))
                              .build(),

                ProductRequest.builder()
                              .quantity(10)
                              .name("Cow Milk")
                              .incomingPrice(BigDecimal.valueOf(1.5))
                              .category(StockCategory.FOOD)
                              .validUntil(LocalDate.now().plus(20, ChronoUnit.DAYS))
                              .build());

        var restTemplate = restTemplateBuilder.build();

        var productsResponse = restTemplate.exchange(BASE_URL + port + PRODUCTS_PATH,
                                                     HttpMethod.POST,
                                                     createHttpEntity(requests, storeId),
                                                     String.class);

        return JsonUtils.fromJson(productsResponse.getBody(), new TypeReference<List<Product>>()
        {
        });
    }
}
