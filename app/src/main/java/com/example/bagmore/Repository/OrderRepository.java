package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.OrderService;

public class OrderRepository {
    public static OrderService getOrderService() {
        return ApiClient.getClient().create(OrderService.class);
    }
}
