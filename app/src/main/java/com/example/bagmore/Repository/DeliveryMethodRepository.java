package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.DeliveryMethodService;

public class DeliveryMethodRepository {
    public static DeliveryMethodService getDeliveryMethodService() {
        return ApiClient.getClient().create(DeliveryMethodService.class);
    }
}
