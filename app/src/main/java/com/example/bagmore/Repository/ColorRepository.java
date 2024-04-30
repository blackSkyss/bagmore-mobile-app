package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.ColorService;

public class ColorRepository {
    public static ColorService getColorService() {
        return ApiClient.getClient().create(ColorService.class);
    }
}
