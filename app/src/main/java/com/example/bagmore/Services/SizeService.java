package com.example.bagmore.Services;

import com.example.bagmore.Models.json.response.JsonSize;

import retrofit2.Call;
import retrofit2.http.GET;

public interface SizeService {
    @GET("user/product/sizes")
    Call<JsonSize> getSizes();
}
