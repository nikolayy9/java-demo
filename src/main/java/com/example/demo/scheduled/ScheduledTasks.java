package com.example.demo.scheduled;

import com.example.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks
{
    private final ProductService productService;

    // every day at 6 am
    @Scheduled(cron = "0 0 6 * * ?")
   // @Scheduled(cron = "* * * * * *")
    public void calculateProductsPrice()
    {
        productService.calculateProductsPrice();
    }

    // every day at 5 am
    @Scheduled(cron = "0 0 5 * * ?")
    // @Scheduled(cron = "* * * * * *")
    public void updateInvalidProducts()
    {
        productService.updateInvalidProducts();
    }

}
