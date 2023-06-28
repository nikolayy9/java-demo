package com.example.demo.service;

import com.example.demo.model.Store;
import com.example.demo.model.StoreRequest;
import com.example.demo.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.demo.utils.CustomStringUtils.generateId;

@RequiredArgsConstructor
@Service
public class StoreService
{
    private final StoreRepository storeRepository;

    public Store createStore(StoreRequest request)
    {
        var store = Store.builder()
                         .id(generateId())
                         .name(request.name())
                         .address(request.address())
                         .build();

        return storeRepository.save(store);
    }

    public Store updateStore(String id, StoreRequest request)
    {
        return storeRepository.findById(id)
                              .map(storeToUpdate ->
                                   {
                                       var store = storeToUpdate.toBuilder()
                                                                .address(request.address())
                                                                .name(request.name())
                                                                .build();

                                       return storeRepository.save(store);
                                   }).orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    public Store getStore(String id)
    {
        return storeRepository.findById(id)
                              .orElseThrow(() -> new IllegalArgumentException("Store not found"));
    }

    public List<Store> getAll()
    {
        return storeRepository.findAll();
    }
}
