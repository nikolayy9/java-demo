package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.model.ProductRequest;
import com.example.demo.service.ProductService;
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
@RequestMapping("/api/v1/products")
@RestController
public class ProductController
{
    private final ProductService productService;

    @PostMapping
    public List<Product> loadProducts(@RequestHeader(name = STORE_ID) String storeId,
                                      @RequestBody List<ProductRequest> requests)
    {
        return productService.loadProducts(storeId, requests);
    }


    @GetMapping
    public List<Product> searchProducts(@RequestHeader(name = STORE_ID) String storeId)
    {
        return productService.searchProducts(storeId);
    }
}

