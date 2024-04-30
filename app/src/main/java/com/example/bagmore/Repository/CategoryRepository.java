package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.CategoryService;

public class CategoryRepository {
    public static CategoryService getCategoryService() {
        return ApiClient.getClient().create(CategoryService.class);
    }
}
