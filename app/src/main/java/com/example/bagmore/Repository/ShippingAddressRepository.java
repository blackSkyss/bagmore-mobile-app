package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.ShippingAddressService;

public class ShippingAddressRepository {
    public static ShippingAddressService getShippingAddressService() {
        return ApiClient.getClient().create(ShippingAddressService.class);
    }
}
