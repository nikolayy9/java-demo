package com.example.demo.controller;

import com.example.demo.model.Store;
import com.example.demo.model.StoreRequest;
import com.example.demo.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/stores")
@RestController
public class StoreController
{
    private final StoreService storeService;

    @PostMapping
    public Store createStore(@RequestBody StoreRequest request)
    {
        return storeService.createStore(request);
    }

    @PatchMapping("/{id}")
    public Store updateStore(@PathVariable(name = "id") String id,
                             @RequestBody StoreRequest request)
    {
        return storeService.updateStore(id, request);
    }

    @GetMapping
    public List<Store> getAll()
    {
        return storeService.getAll();
    }

    @GetMapping("/{id}")
    public Store getStore(@PathVariable(name = "id") String id)
    {
        return storeService.getStore(id);
    }
}
