package com.example.demo.service;

import com.example.demo.model.Checkout;
import com.example.demo.model.CheckoutRequest;
import com.example.demo.repository.CheckoutRepository;
import com.example.demo.utils.CustomStringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.CustomStringUtils.generateId;
import static com.example.demo.utils.CustomStringUtils.isValidString;

@Service
@RequiredArgsConstructor
public class CheckoutService
{
    private final CheckoutRepository checkoutRepository;
    private final CashierService cashierService;

    @Transactional
    public List<Checkout> createCheckouts(String storeId, List<CheckoutRequest> requests)
    {
        var checkouts = requests.stream()
                                .map(request -> buildCheckout(storeId, request))
                                .toList();

        return checkoutRepository.saveAll(checkouts);
    }

    public Checkout update(String storeId, String id, CheckoutRequest request)
    {
        return checkoutRepository.findByStoreIdAndId(storeId, id)
                                 .map(checkout ->
                                      {
                                          var cashier = cashierService.getByCashierId(storeId, request.cashierId())
                                                                      .orElse(null);

                                          return checkoutRepository.update(storeId,
                                                                           request.name(),
                                                                           cashier,
                                                                           id);
                                      })
                                 .orElseThrow();
    }

    private Checkout buildCheckout(String storeId, CheckoutRequest request)
    {
        if (!CustomStringUtils.isValidString(request.name()))
        {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        var cashierId = request.cashierId();
        var checkout = Checkout.builder()
                               .storeId(storeId)
                               .id(generateId())
                               .createdAt(LocalDate.now())
                               .name(request.name());

        if (isValidString(cashierId))
        {
            cashierService.getByCashierId(storeId, cashierId)
                          .ifPresent(checkout::cashier);
        }

        return checkout.build();
    }

    public List<Checkout> getAllCheckouts(String storeId)
    {
        return checkoutRepository.findAllByStoreId(storeId);
    }

    public Optional<Checkout> getById(String storeId, String id)
    {
        return checkoutRepository.findByStoreIdAndId(storeId, id);
    }
}
