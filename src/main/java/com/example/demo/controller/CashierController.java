package com.example.demo.controller;

import com.example.demo.model.Cashier;
import com.example.demo.model.CashierRequest;
import com.example.demo.service.CashierService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.demo.utils.CustomHeaders.STORE_ID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/cashiers")
@RestController
public class CashierController
{

    private final CashierService cashierService;

    @PostMapping
    public List<Cashier> createCashiers(@RequestHeader(name = STORE_ID) String storeId,
                                        @RequestBody List<CashierRequest> requests)
    {

        return cashierService.createCashiers(storeId, requests);
    }

    @GetMapping
    public List<Cashier> getAllCashiers(@RequestHeader(name = STORE_ID) String storeId)
    {
        return cashierService.getAllCashiers(storeId);
    }
}
