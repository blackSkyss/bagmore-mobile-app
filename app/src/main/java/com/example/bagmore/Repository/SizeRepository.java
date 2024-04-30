package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.SizeService;

public class SizeRepository {
    public static SizeService getSizeService() {
        return ApiClient.getClient().create(SizeService.class);
    }
}
