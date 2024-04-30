package com.example.bagmore.Services;

import com.example.bagmore.API.ApiClient;
import com.example.bagmore.Models.json.response.JsonCategory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CategoryService {
    @GET("user/product/categories")
    Call<JsonCategory> getCategories(@Header(ApiClient.AUTH_HEADER) String accessToken);
}
