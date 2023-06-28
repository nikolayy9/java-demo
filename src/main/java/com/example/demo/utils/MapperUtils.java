package com.example.demo.utils;

import com.example.demo.model.Bill;
import com.example.demo.model.BillProduct;
import com.example.demo.model.BillProductResponse;
import com.example.demo.model.BillResponse;
import com.example.demo.model.CashierResponse;
import com.example.demo.model.Checkout;
import com.example.demo.model.CheckoutResponse;
import com.example.demo.model.ProductResponse;

public class MapperUtils
{
    public static BillResponse fromProductBill(Bill bill)
    {
        var billProducts = bill.products().stream()
                               .map(MapperUtils::fromBillProduct)
                               .toList();

        return BillResponse.builder()
                           .id(bill.id())
                           .billProductsResponse(billProducts)
                           .checkoutResponse(fromCheckout(bill.checkout()))
                           .createdAt(bill.createdAt())
                           .totalSum(bill.totalSum())
                           .build();
    }

    private static BillProductResponse fromBillProduct(BillProduct billProduct)
    {
        return BillProductResponse.builder()
                                  .quantity(billProduct.quantity())
                                  .totalSum(billProduct.totalSum())
                                  .productResponse(fromProduct(billProduct))
                                  .build();
    }

    private static ProductResponse fromProduct(BillProduct billProduct)
    {
        var product = billProduct.product();

        return ProductResponse.builder()
                              .name(product.name())
                              .sellingPrice(product.sellingPrice())
                              .validUntil(product.validUntil())
                              .build();
    }

    private static CheckoutResponse fromCheckout(Checkout checkout)
    {
        return CheckoutResponse.builder()
                               .name(checkout.name())
                               .cashier(CashierResponse.builder()
                                                       .name(checkout.cashier().name())
                                                       .build())
                               .build();
    }
}
