package com.example.bagmore.Services;

import com.example.bagmore.Models.json.response.JsonCategory;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CategoryService {
    @GET("user/product/categories")
    Call<JsonCategory> getCategories();
}
