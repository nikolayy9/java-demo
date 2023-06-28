package com.example.demo.controller;

import com.example.demo.model.Bill;
import com.example.demo.model.BillRequest;
import com.example.demo.model.BillResponse;
import com.example.demo.service.BillService;
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
@RequestMapping("/api/v1/bills")
@RestController
public class BillController
{
    private final BillService billService;

    @PostMapping
    public BillResponse createBill(@RequestHeader(name = STORE_ID) String storeId,
                                   @RequestBody BillRequest request)
    {
        return billService.createBill(storeId, request);
    }

    @GetMapping
    public List<Bill> searchBills(@RequestHeader(name = STORE_ID) String storeId)
    {
        return billService.searchBills(storeId);
    }
}
