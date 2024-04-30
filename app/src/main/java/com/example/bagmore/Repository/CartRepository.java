package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.CartService;

public class CartRepository {
    public static CartService getCartService() {
        return ApiClient.getClient().create(CartService.class);
    }
}
