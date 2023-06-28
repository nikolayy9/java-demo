package com.example.demo.service;

import com.example.demo.model.Cashier;
import com.example.demo.model.CashierRequest;
import com.example.demo.repository.CashierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.CustomStringUtils.generateId;

@Service
@RequiredArgsConstructor
public class CashierService
{
    private final CashierRepository cashierRepository;

    @Transactional
    public List<Cashier> createCashiers(String storeId, List<CashierRequest> requests)
    {
        var cashiersToSave = requests.stream()
                                     .map(request -> buildCashier(storeId, request))
                                     .toList();

        return cashierRepository.saveAll(cashiersToSave);
    }

    public Optional<Cashier> getByCashierId(String storeId, String id)
    {
        return cashierRepository.findByStoreIdAndId(storeId, id);
    }

    public List<Cashier> getAllCashiers(String storeId)
    {
        return cashierRepository.findAllByStoreId(storeId);
    }

    private Cashier buildCashier(String storeId, CashierRequest request)
    {
        return Cashier.builder()
                      .id(generateId())
                      .storeId(storeId)
                      .name(request.name())
                      .salary(request.salary())
                      .createdAt(LocalDate.now())
                      .build();
    }
}
