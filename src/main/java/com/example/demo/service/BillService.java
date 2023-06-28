package com.example.demo.service;

import com.example.demo.exceptions.CashierNotFoundException;
import com.example.demo.exceptions.CustomerCashException;
import com.example.demo.exceptions.InsufficientQuantityException;
import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Bill;
import com.example.demo.model.BillProduct;
import com.example.demo.model.BillRequest;
import com.example.demo.model.BillResponse;
import com.example.demo.repository.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.example.demo.utils.CustomStringUtils.generateId;
import static com.example.demo.utils.MapperUtils.fromProductBill;

@Service
@RequiredArgsConstructor
public class BillService
{
    private final BillRepository billRepository;
    private final ProductService productService;
    private final CashierService cashierService;
    private final CheckoutService checkoutService;

    @Transactional
    public BillResponse createBill(String storeId,
                                   BillRequest request)
    {
        var cashier = cashierService.getByCashierId(storeId, request.cashierId())
                                    .orElseThrow(() -> new CashierNotFoundException(String.format("Cashier with id: %s not found",
                                                                                                  request.cashierId())));

        var checkout = checkoutService.getById(storeId, request.checkoutId())
                                      .orElseThrow(() -> new CashierNotFoundException(String.format("Checkout with id: %s not found",
                                                                                                    request.cashierId())));

        List<BillProduct> allProducts = request.products()
                                               .entrySet()
                                               .stream()
                                               .map(entry -> calculateSumOfProducts(storeId, entry))
                                               .toList();

        BigDecimal totalSum = allProducts.stream()
                                         .map(BillProduct::totalSum)
                                         .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (request.customerCash().compareTo(totalSum) < 0)
        {
            throw new CustomerCashException("Not enough money");
        }

        var saveBillRequest = Bill.builder()
                                  .cashier(cashier)
                                  .storeId(storeId)
                                  .id(generateId())
                                  .totalSum(totalSum)
                                  .checkout(checkout)
                                  .products(allProducts)
                                  .createdAt(LocalDateTime.now())
                                  .build();

        var bill = billRepository.save(saveBillRequest);

        request.products().forEach((key, value) -> productService.updateProductQuantity(storeId, key, value));
        saveAsFile(bill.toString(), bill.id());
        return fromProductBill(bill);
    }

    private void saveAsFile(String content, String filename)
    {
        try
        {
            Path path = Paths.get(filename);
            Files.write(path, content.getBytes());
        }
        catch (IOException e)
        {
            throw new RuntimeException("Error while saving file");
        }
    }

    private BillProduct calculateSumOfProducts(String storeId, Map.Entry<String, Integer> e)
    {
        var productId = e.getKey();
        var requestedQuantity = e.getValue();

        var product = productService.getById(storeId, productId)
                                    .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id: %s not found",
                                                                                                  productId)));

        if (product.quantity() < requestedQuantity)
        {
            throw new InsufficientQuantityException(String.format("Product: %s has %d quantity, but %d was requested",
                                                                  product,
                                                                  product.quantity(),
                                                                  requestedQuantity));
        }

        var sumOfProducts = product.sellingPrice().multiply(BigDecimal.valueOf(requestedQuantity));

        return BillProduct.builder()
                          .product(product)
                          .totalSum(sumOfProducts)
                          .quantity(requestedQuantity)
                          .build();
    }

    public List<Bill> searchBills(String storeId)
    {
        return billRepository.findAllByStoreId(storeId);
    }
}
