package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.WishListService;

public class WishListRepository {
    public static WishListService getWishListService() {
        return ApiClient.getClient().create(WishListService.class);
    }
}
