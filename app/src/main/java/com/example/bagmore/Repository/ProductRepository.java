package com.example.bagmore.Repository;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Services.ProductService;

public class ProductRepository {
    public static ProductService getProductService(){
        return ApiClient.getClient().create(ProductService.class);
    }
}
