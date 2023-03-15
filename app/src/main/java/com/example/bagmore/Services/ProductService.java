package com.example.bagmore.Services;

import com.example.bagmore.Models.json.response.JsonProductViewModel;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {
    @GET("products")
    Call<JsonProductViewModel> getProducts();
}
