package com.example.demo.service;

import com.example.demo.exceptions.ProductNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.model.ProductRequest;
import com.example.demo.model.StockCategory;
import com.example.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static com.example.demo.utils.CustomStringUtils.generateId;

@Service
@RequiredArgsConstructor
public class ProductService
{
    private final ProductRepository productRepository;

    @Transactional
    public List<Product> loadProducts(String storeId, List<ProductRequest> requests)
    {
        var productsToSave = requests.stream()
                                     .map(request -> Product.builder()
                                                            .id(generateId())
                                                            .storeId(storeId)
                                                            .name(request.name())
                                                            .category(request.category())
                                                            .quantity(request.quantity())
                                                            .createdAt(LocalDateTime.now())
                                                            .validUntil(request.validUntil())
                                                            .incomingPrice(request.incomingPrice())
                                                            .sellingPrice(calculateSellingPrice(request))
                                                            .eligibleForSale(request.validUntil().isAfter(LocalDate.now()))
                                                            .build())
                                     .toList();

        return productRepository.saveAll(productsToSave);
    }

    private BigDecimal calculateSellingPrice(ProductRequest request)
    {
        if (request.category() == StockCategory.FOOD)
        {
            return request.incomingPrice().multiply(BigDecimal.valueOf(1.30));  // 30% markup
        }
        return request.incomingPrice().multiply(BigDecimal.valueOf(1.20));  // 20% markup
    }

    public Optional<Product> getById(String storeId, String id)
    {
        return productRepository.findByStoreIdAndIdAndEligibleForSaleIsTrue(storeId, id);
    }

    public void calculateProductsPrice()
    {
        var validFrom = LocalDate.now();
        var validTo = validFrom.plus(7, ChronoUnit.DAYS);

        var productsToUpdate = productRepository.findAllByValidUntilBetweenAndEligibleForSaleIsTrue(validFrom, validTo);

        productsToUpdate.stream()
                        .map(this::calculateProductDiscount)
                        .forEach(productRepository::save);
    }

    public void updateInvalidProducts()
    {
        var invalidProducts = productRepository.findAllByValidUntilAfter(LocalDate.now());

        invalidProducts.stream()
                       .map(product -> product.toBuilder()
                                              .eligibleForSale(false)
                                              .build())
                       .forEach(productRepository::save);
    }

    private Product calculateProductDiscount(Product product)
    {
        var validDaysLeft = Period.between(LocalDate.now(), product.validUntil())
                                  .getDays();

        var discountPercent = switch (validDaysLeft)
        {
            case 1 -> 0.7;
            case 2 -> 0.6;
            case 3 -> 0.5;
            case 4 -> 0.4;
            case 5 -> 0.3;
            case 6 -> 0.2;
            case 7 -> 0.1;
            default -> 0.0; // Default case added
        };
        if (discountPercent == 0.0)
        {
            return product;
        }
        else
        {
            var oldSellingPrice = product.sellingPrice();
            var newSellingPrice = oldSellingPrice.subtract(oldSellingPrice.multiply(BigDecimal.valueOf(discountPercent)));

            return product.toBuilder()
                          .sellingPrice(newSellingPrice)
                          .build();
        }
    }

    public void updateProductQuantity(String storeId, String productId, int soldQuantity)
    {
        var productToUpdate = productRepository.findByStoreIdAndIdAndEligibleForSaleIsTrue(storeId, productId)
                                               .orElseThrow(() -> new ProductNotFoundException(String.format("Product with id: %s not found",
                                                                                                             productId)));

        var newQuantity = productToUpdate.quantity() - soldQuantity;

        var product = productToUpdate.toBuilder()
                                     .quantity(newQuantity)
                                     .build();

        productRepository.save(product);
    }

    public List<Product> searchProducts(String storeId)
    {
        return productRepository.findAllByStoreId(storeId);
    }
}
