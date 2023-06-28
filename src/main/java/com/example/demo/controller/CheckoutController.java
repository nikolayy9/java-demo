package com.example.demo.controller;

import com.example.demo.model.Checkout;
import com.example.demo.model.CheckoutRequest;
import com.example.demo.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.utils.CustomHeaders.STORE_ID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/checkouts")
@RestController
public class CheckoutController
{
    private final CheckoutService checkoutService;

    @PostMapping
    public List<Checkout> createCheckouts(@RequestHeader(name = STORE_ID) String storeId,
                                          @RequestBody List<CheckoutRequest> requests)
    {
        return checkoutService.createCheckouts(storeId, requests);
    }

    @PatchMapping("/{id}")
    public Checkout updateCheckout(@RequestHeader(name = STORE_ID) String storeId,
                                   @PathVariable(name = "id") String id,
                                   @RequestBody CheckoutRequest request)
    {
        return checkoutService.update(storeId, id, request);
    }

    @GetMapping
    public List<Checkout> getAllCheckouts(@RequestHeader(name = STORE_ID) String storeId)
    {
        return checkoutService.getAllCheckouts(storeId);
    }
}
